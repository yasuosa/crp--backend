package com.rpy.system.utils;

import com.rpy.system.common.ActiveUser;
import com.rpy.system.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.swing.*;
import java.util.List;

/**
 * @Auther 任鹏宇
 * @Date 2020/2/26
 * 获取session里面的数据
 */
public class SessionDataUtils {


    public static ActiveUser getActiveUser(){
        return (ActiveUser) SecurityUtils.getSubject().getPrincipal();
    }

    public static User getUser(){
        return  getActiveUser().getUser();
    }

    public static List<String> getRoles(){
        return getActiveUser().getRoles();
    }

    public static List<String> getPermissons(){
        return getActiveUser().getPermissions();
    }
}
