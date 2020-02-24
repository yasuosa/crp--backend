package com.rpy.system.service;

import com.rpy.system.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
public interface UserService extends IService<User>{

    /**
     * 根据用户登陆名
     * 查询用户信息
     */
    User queryUserByLoginName(String loginname);
}
