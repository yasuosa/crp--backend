package com.rpy.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @Auther 任鹏宇
 * @Date 2020/2/26
 * 解决跨域
 */
@Configuration
public class CorsAutoConfig {

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource=new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration=new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedOrigin("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);

        CorsFilter corsFilter=new CorsFilter(urlBasedCorsConfigurationSource);
        return corsFilter;
    }
}
