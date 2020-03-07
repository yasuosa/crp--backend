package com.rpy.system.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import com.rpy.system.common.ActiveUser;
import com.rpy.system.common.ResultObj;
import com.rpy.system.domain.Loginfo;
import com.rpy.system.domain.User;
import com.rpy.system.service.LoginfoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther 任鹏宇
 * @Date 2020/2/24
 */
@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginfoService loginfoService;

    @Autowired
    private StringRedisTemplate redisTemplate;


    /**
     * 用户登陆
     */
    @RequestMapping(value = "doLogin",method = RequestMethod.POST)
    public ResultObj doLogin(String loginname, String password,String keyCode,String captcha, HttpServletRequest request){
        try {
            ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
            String code = opsForValue.get(keyCode);
            if(null ==code ){
                return new ResultObj(-1,"验证码过期");
            }
            if(!captcha.equals(code)){
                return new ResultObj(-1,"验证码错误");
            }
            Subject subject = SecurityUtils.getSubject();
            AuthenticationToken loginToken=new UsernamePasswordToken(loginname,password);
            subject.login(loginToken);
            ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
            User user = activeUser.getUser();
            //得到shiro的sessionid==token
            String token= subject.getSession().getId().toString();
            //写入登陆日志
            Loginfo loginfo=new Loginfo();
            loginfo.setLoginname(user.getName()+"-"+user.getLoginname());
            loginfo.setLoginip(request.getLocalAddr());
            loginfo.setLogintime(new Date());
            loginfoService.save(loginfo);
            Map<String,Object> map=new HashMap<>();
            map.put("token",token);
            map.put("permissions",activeUser.getPermissions());
            map.put("type",user.getType());
            map.put("username",user.getName());
            return new ResultObj(200,"登陆成功",map);
        }catch (AuthenticationException e){
            e.printStackTrace();
            return new ResultObj(-1,"登陆或密码不正确");
        }
    }


    /**
     * 返回验证码
     */
    @RequestMapping(value = "captcha",method =RequestMethod.GET)
    public void getCode(String keyCode, HttpServletResponse response) throws IOException {
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(100, 30, 4, 4);
        captcha.write(response.getOutputStream());
        String code = captcha.getCode();
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        opsForValue.set(keyCode,code, Duration.ofMinutes(1));
    }

    /**
     * 判断当前token是否登陆
     */
    @RequestMapping(value = "checkLogin",method = RequestMethod.POST)
    public ResultObj checkLogin(){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            return ResultObj.IS_LOGIN;
        }else {
            return ResultObj.UN_LOGIN;
        }
    }
}
