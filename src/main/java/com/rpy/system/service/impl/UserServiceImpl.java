package com.rpy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rpy.system.domain.User;
import com.rpy.system.mapper.UserMapper;
import com.rpy.system.service.UserService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    private Log log= LogFactory.getLog(UserServiceImpl.class.getSimpleName());


    @Override
    public User queryUserByLoginName(String loginname) {
        UserMapper userMapper = getBaseMapper();
        if(StringUtils.isBlank(loginname)){
            log.error("登陆名不能为空");
            return null;
        }
        QueryWrapper<User> qw=new QueryWrapper<>();
        qw.eq("loginname",loginname);
        User user = userMapper.selectOne(qw);
        return user;
    }
}
