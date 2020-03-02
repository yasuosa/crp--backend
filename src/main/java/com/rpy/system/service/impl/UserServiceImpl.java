package com.rpy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rpy.system.common.AppUtils;
import com.rpy.system.common.Constant;
import com.rpy.system.common.DataGirdView;
import com.rpy.system.domain.Dept;
import com.rpy.system.mapper.DeptMapper;
import com.rpy.system.mapper.RoleMapper;
import com.rpy.system.service.DeptService;
import com.rpy.system.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.Serializable;
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

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;



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

    @Override
    public DataGirdView queryAllUser(UserVo userVo) {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        IPage<User> page=new Page<>(userVo.getPage(),userVo.getLimit());
        queryWrapper.eq("type", Constant.USER_TYPE_NORMAL);
        queryWrapper.eq(userVo.getDeptid()!=null,"deptid",userVo.getDeptid());
        queryWrapper.like(StringUtils.isNotBlank(userVo.getName()),"name",userVo.getName());
        queryWrapper.like(StringUtils.isNotBlank(userVo.getRemark()),"remark",userVo.getRemark());
        queryWrapper.like(StringUtils.isNotBlank(userVo.getAddress()),"address",userVo.getAddress());
        userMapper.selectPage(page,queryWrapper);
        List<User> records = page.getRecords();
        DeptService deptService= AppUtils.getContext().getBean(DeptService.class);
        for (User record : records) {
            if(null != record.getDeptid()){
                Dept dept = deptService.getById(record.getDeptid());
                record.setDeptName(dept.getTitle());
            }
        }
        return new DataGirdView(page.getTotal(), records);
    }

    @Override
    public User saveUser(User user) {
        userMapper.insert(user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        userMapper.updateById(user);
        return user;
    }

    @Override
    public Integer queryUserMaxOrderNum() {
        return userMapper.queryUserMaxOrderNum();
    }

    @Override
    public void saveUserRole(Integer uid, Integer[] rids) {
        roleMapper.deleteUserMenuByUid(uid);
        if(null !=rids && rids.length>0) {
            roleMapper.saveUserRole(uid, rids);
        }
    }

    @Override
    public boolean removeById(Serializable id) {
        //根据用户id删除 角色和用户之间的关系
        roleMapper.deleteUserMenuByUid(id);
        return super.removeById(id);
    }
}
