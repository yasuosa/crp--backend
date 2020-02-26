package com.rpy.system.controller;

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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.net.util.IPAddressUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Auther 任鹏宇
 * @Date 2020/2/24
 */
@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginfoService loginfoService;


    /**
     * 用户登陆
     */
    @RequestMapping(value = "doLogin",method = RequestMethod.POST)
    public ResultObj doLogin(String loginname, String password, HttpServletRequest request){
        try {
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
            return new ResultObj(200,"登陆成功",token);
        }catch (AuthenticationException e){
            e.printStackTrace();
            return new ResultObj(-1,"登陆或密码不正确");
        }
    }


    /**
     * 返回验证码
     */

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
