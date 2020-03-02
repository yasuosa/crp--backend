package com.rpy.system.service;

import com.rpy.system.common.DataGirdView;
import com.rpy.system.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rpy.system.vo.UserVo;

public interface UserService extends IService<User>{

    /**
     * 根据用户登陆名
     * 查询用户信息
     */
    User queryUserByLoginName(String loginname);

    DataGirdView queryAllUser(UserVo userVo);

    User saveUser(User user);

    User updateUser(User user);

    Integer queryUserMaxOrderNum();


    void saveUserRole(Integer uid, Integer[] rids);
}
