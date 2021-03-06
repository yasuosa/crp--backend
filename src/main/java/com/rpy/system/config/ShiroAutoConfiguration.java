package com.rpy.system.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import com.rpy.system.flitter.ShiroLoginFilter;
import com.rpy.system.realm.UserRealm;
import com.rpy.system.shiro.OptionsAccessControllerFilter;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.IRedisManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.filter.DelegatingFilterProxy;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import lombok.Data;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Auther 任鹏宇
 * @Date 2020/2/24
 */

@Configuration
@ConditionalOnWebApplication(type = Type.SERVLET)
@ConditionalOnClass(value = { SecurityManager.class })
@ConfigurationProperties(prefix = "shiro")
@Data
public class ShiroAutoConfiguration {

	private static final String SHIRO_DIALECT = "shiroDialect";
	private static final String SHIRO_FILTER = "shiroFilter";
	private String hashAlgorithmName = "md5";// 加密方式
	private int hashIterations = 2;// 散列次数
	private String loginUrl = "/index.html";// 默认的登陆页面

	private String[] anonUrls;
	private String logOutUrl;
	private String[] authcUlrs;


	@Autowired
	@Lazy
	private RedisProperties redisProperties;

	/**
	 * 声明凭证匹配器
	 */
	@Bean("credentialsMatcher")
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
		credentialsMatcher.setHashAlgorithmName(hashAlgorithmName);
		credentialsMatcher.setHashIterations(hashIterations);
		return credentialsMatcher;
	}

	/**
	 * 声明userRealm
	 */
	@Bean("userRealm")
	public UserRealm userRealm(CredentialsMatcher credentialsMatcher) {
		UserRealm userRealm = new UserRealm();
		// 注入凭证匹配器
		userRealm.setCredentialsMatcher(credentialsMatcher);
		return userRealm;
	}

	/**
	 * 配置SecurityManager
	 */
	@Bean("securityManager")
	public SecurityManager securityManager(DefaultWebSessionManager defaultWebSessionManager, SessionDAO sessionDAO, UserRealm userRealm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 注入userRealm
		securityManager.setRealm(userRealm);
		defaultWebSessionManager.setSessionDAO(sessionDAO);
		securityManager.setSessionManager(defaultWebSessionManager);
		return securityManager;
	}

	/**
	 * 配置shiro的过滤器
	 */
	@Bean(SHIRO_FILTER)
	public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
		// 设置安全管理器
		factoryBean.setSecurityManager(securityManager);
		// 设置未登陆的时要跳转的页面
		factoryBean.setLoginUrl(loginUrl);
		Map<String, String> filterChainDefinitionMap = new HashMap<>();
		// 设置放行的路径
		if (anonUrls != null && anonUrls.length > 0) {
			for (String anon : anonUrls) {
				filterChainDefinitionMap.put(anon, "anon");
			}
		}
		// 设置登出的路径
		if (null != logOutUrl) {
			filterChainDefinitionMap.put(logOutUrl, "logout");
		}
		// 设置拦截的路径
		if (authcUlrs != null && authcUlrs.length > 0) {
			for (String authc : authcUlrs) {
				filterChainDefinitionMap.put(authc, "authc");
				//如果没有此类 建议不要加入 否则创建不来
//				filterChainDefinitionMap.put(authc, "options");
			}
		}
		Map<String, Filter> filters=new HashMap<>();
		filters.put("authc", new ShiroLoginFilter());
		//配置过滤器
		factoryBean.setFilters(filters);
		factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return factoryBean;
	}

	/**
	 * 注册shiro的委托过滤器，相当于之前在web.xml里面配置的
	 * 
	 * @return
	 */
	@Bean
	public FilterRegistrationBean<DelegatingFilterProxy> delegatingFilterProxy() {
		FilterRegistrationBean<DelegatingFilterProxy> filterRegistrationBean = new FilterRegistrationBean<DelegatingFilterProxy>();
		DelegatingFilterProxy proxy = new DelegatingFilterProxy();
		proxy.setTargetFilterLifecycle(true);
		proxy.setTargetBeanName(SHIRO_FILTER);
		filterRegistrationBean.setFilter(proxy);
		return filterRegistrationBean;
	}

	/* 加入注解的使用，不加入这个注解不生效--开始 */
	/**
	 * 
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	@Bean
	public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}
	/* 加入注解的使用，不加入这个注解不生效--结束 */

	/**
	 * 使用Redis  来存储登录的信息
	 *sessionDao 还需要设置给sessionManager
	 */
	@Bean
	public SessionDAO redisSessionDAO(IRedisManager redisManager) {
		RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
		redisSessionDAO.setRedisManager(redisManager); //操作那个redis
		redisSessionDAO.setExpire(2*24*3600); // 用户的登录信息保存多久？ 7 天
		//       redisSessionDAO.setKeySerializer(keySerializer); jdk
		//       redisSessionDAO.setValueSerializer(valueSerializer);jdk
		return redisSessionDAO ;
	}
	@Bean
	public IRedisManager redisManager() {
		RedisManager redisManager = new RedisManager();
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(redisProperties.getJedis().getPool().getMaxActive()); // 链接池的最量 20 ，并发特别大时，连接池的数据可以最大增加20个
		jedisPoolConfig.setMaxIdle(redisProperties.getJedis().getPool().getMaxIdle());// 连接池的最大剩余量15个 ：并发不大，池里面的对象用不上，里面对象太多了。浪费空间
		jedisPoolConfig.setMinIdle(redisProperties.getJedis().getPool().getMinIdle()); // 连接池初始就有10 个
		JedisPool jedisPool = new JedisPool(jedisPoolConfig, redisProperties.getHost(), redisProperties.getPort(),2000,redisProperties.getPassword());
		redisManager.setJedisPool(jedisPool );
		return redisManager ;
	}
}
