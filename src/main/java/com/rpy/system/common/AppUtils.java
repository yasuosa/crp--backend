package com.rpy.system.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Auther 任鹏宇
 * @Date 2020/3/2
 */

/**
 * 当实现这个接口之后 iod容器初始化时 会回调setApplicationContext 把ioc容器对象
 * 传到里面
 */
@Component
public class AppUtils implements ApplicationContextAware {

    private static ApplicationContext context;
    public static ApplicationContext getContext(){
        return context;
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context=applicationContext;
    }
}
