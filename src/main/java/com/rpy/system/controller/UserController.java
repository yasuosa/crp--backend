package com.rpy.system.controller;

import com.rpy.system.common.ActiveUser;
import com.rpy.system.common.Constant;
import com.rpy.system.common.DataGirdView;
import com.rpy.system.common.ResultObj;
import com.rpy.system.domain.User;
import com.rpy.system.service.UserService;
import com.rpy.system.utils.MD5Utils;
import com.rpy.system.utils.SessionDataUtils;
import com.rpy.system.vo.UserVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Auther 任鹏宇
 * @Date 2020/2/27
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    @RequestMapping(value = "loadAllUser" , method = RequestMethod.GET)
    public DataGirdView loadAllUser(UserVo userVo){
        return this.userService.queryAllUser(userVo);
    }



    @RequestMapping(value = "addUser",method = RequestMethod.POST)
    public ResultObj addUser(User user){
        try {
            user.setSalt(MD5Utils.createUUID());
            user.setPwd(MD5Utils.md5(Constant.DEFALUT_PWD,user.getSalt(),2));
            user.setImgpath(Constant.DEFAULT_TITLE_IMAGE);
            user.setAvailable(Constant.AVAILABLE_TRUE);
            user.setType(Constant.USER_TYPE_NORMAL);
            userService.saveUser(user);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_FAIL;
        }
    }




    @RequestMapping(value = "updateUser",method = RequestMethod.POST)
    public ResultObj updateUser(User user){

        if(null == user || user.getId()==null){
            return ResultObj.UPDATE_WRONG;
        }
        try {
            userService.updateUser(user);
            //TODO  redis里面的用户信息修改
//            Integer id = user.getId();
//            ActiveUser activeUser = SessionDataUtils.getActiveUser();
//            if(activeUser.getUser().getId().equals(id)){
//                Serializable token = SecurityUtils.getSubject().getSession().getId();
//                ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
//                user=userService.getById(id);
//                activeUser.setUser(user);
//                opsForValue.set(Constant.SHIOR_USER_STUFF+token,activeUser);
//            }
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_FAIL;
        }
    }


    @RequestMapping(value = "deleteUser",method = RequestMethod.POST)
    public ResultObj delUser(Integer id){
        if(null == id){
            return ResultObj.DELETE_WRONG;
        }
        try {
            userService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_FAIL;
        }
    }


    /**
     * 查询最大排序码
     * @return
     */
    @RequestMapping(value = "queryUserMaxOrderNum",method = RequestMethod.GET)
    public DataGirdView queryUserMaxOrderNum(){
        Integer ordernum=userService.queryUserMaxOrderNum();
        return new DataGirdView(ordernum+1);
    }


    /**
     * 重置密码
     * @param id
     * @return
     */
    @RequestMapping(value = "resetUserPwd",method = RequestMethod.POST)
    public ResultObj resetUserPwd(Integer id){
        try {
            User user=new User();
            user.setId(id);
            user.setSalt(MD5Utils.createUUID());
            user.setPwd(MD5Utils.md5(Constant.DEFALUT_PWD,user.getSalt(),2));
            this.userService.updateById(user);
            return ResultObj.RESET_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.RESET_FAIL;
        }
    }


    @RequestMapping(value = "saveUserRole",method = RequestMethod.POST)
    public ResultObj saveUserRole(Integer uid,Integer[] rids){
        try {
            userService.saveUserRole(uid,rids);
            return ResultObj.DISPATCH_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DISPATCH_FAIL;
        }
    }


    /**
     * 得到当前用户信息
     */
    @RequestMapping(value = "getCurrentUser",method = RequestMethod.GET)
    public DataGirdView getCurrentUser(){
        ActiveUser activeUser= (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        return new DataGirdView(activeUser.getUser());
    }
}
