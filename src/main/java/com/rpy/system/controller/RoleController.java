package com.rpy.system.controller;

import com.rpy.system.common.Constant;
import com.rpy.system.common.DataGirdView;
import com.rpy.system.common.ResultObj;
import com.rpy.system.domain.Role;
import com.rpy.system.service.RoleService;
import com.rpy.system.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/**
 * @Auther 任鹏宇
 * @Date 2020/2/27
 */
@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;


    @RequestMapping(value = "loadAllRole" , method = RequestMethod.GET)
    public DataGirdView loadAllRole(RoleVo roleVo){
        return this.roleService.queryAllRole(roleVo);
    }



    @RequestMapping(value = "addRole",method = RequestMethod.POST)
    public ResultObj addRole(Role role){
        try {
            role.setCreatetime(new Date());
            role.setAvailable(Constant.AVAILABLE_TRUE);
            roleService.saveRole(role);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_FAIL;
        }
    }




    @RequestMapping(value = "updateRole",method = RequestMethod.POST)
    public ResultObj updateRole(Role role){
        if(null == role || role.getId()==null){
            return ResultObj.UPDATE_WRONG;
        }
        try {
            roleService.updateRole(role);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_FAIL;
        }
    }


    @RequestMapping(value = "delRole",method = RequestMethod.POST)
    public ResultObj delRole(Integer id){
        if(null == id){
            return ResultObj.DELETE_WRONG;
        }
        try {
            roleService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_FAIL;
        }
    }


    @RequestMapping(value = "batchDelRole",method = RequestMethod.POST)
    public ResultObj batchDelRole(Integer[] ids){
        if(null ==ids || ids.length==0){
            return ResultObj.DELETE_WRONG;
        }
        try {
            for (Integer id : ids) {
                roleService.removeById(id);
            }
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_FAIL;
        }
    }


    /**
     * 根据角色id查询菜单
     * @param id
     * @return
     */
    @RequestMapping(value = "queryMenuIdsByRid",method = RequestMethod.GET)
    public Object queryMenuIdsByRid(Integer id){
        List<Integer> mids=roleService.queryMenuIdsByRid(id);
        return new DataGirdView(mids);
    }

    @RequestMapping(value = "saveRoleMenu",method = RequestMethod.POST)
    public ResultObj saveRoleMenu(Integer rid,Integer[] mids){
        try {
            roleService.saveRoleMenu(rid,mids);
            return ResultObj.DISPATCH_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DISPATCH_FAIL;
        }
    }


    @RequestMapping(value = "loadAllAvailableRoleNoPage",method = RequestMethod.GET)
    public DataGirdView loadAllAvailableRoleNoPage(Integer userid){
        return roleService.loadAllAvailableRoleNoPage(userid);
    }

}
