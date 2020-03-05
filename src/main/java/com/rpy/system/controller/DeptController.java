package com.rpy.system.controller;

import com.rpy.system.common.Constant;
import com.rpy.system.common.DataGirdView;
import com.rpy.system.common.ResultObj;
import com.rpy.system.domain.Dept;
import com.rpy.system.service.DeptService;
import com.rpy.system.vo.DeptVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther 任鹏宇
 * @Date 2020/2/27
 */
@RestController
@RequestMapping("dept")
public class DeptController {

    @Autowired
    private DeptService deptService;


    @RequestMapping(value = "loadAllDept" , method = RequestMethod.GET)
    public DataGirdView loadAllDept(DeptVo deptVo){
        return this.deptService.queryAllDept(deptVo);
    }


    @RequestMapping(value = "queryDeptMaxOrderNum",method = RequestMethod.GET)
    public Object queryDeptMaxOrderNum(){
        Integer maxVal=deptService.queryDeptMaxOrderNum();
        return new DataGirdView(maxVal+1);
    }


    @RequiresPermissions("dept:add")
    @RequestMapping(value = "addDept",method = RequestMethod.POST)
    public ResultObj addDept(Dept dept){
        try {
            dept.setSpread(Constant.SPREAD_FALSE);
            dept.setAvailable(Constant.AVAILABLE_TRUE);
            deptService.saveDept(dept);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_FAIL;
        }
    }

    @RequestMapping(value = "getOneDeptById" ,method = RequestMethod.GET)
    public DataGirdView getOneDeptById(Integer id){
        Dept dept = deptService.getById(id);
        return new DataGirdView(dept);
    }

    @RequiresPermissions("dept:update")
    @RequestMapping(value = "updateDept",method = RequestMethod.POST)
    public ResultObj updateDept(Dept dept){
        if(null == dept || dept.getId()==null){
            return ResultObj.UPDATE_WRONG;
        }
        try {
            deptService.updateDept(dept);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_FAIL;
        }
    }

    @RequestMapping(value = "getDeptChildrenCountById",method = RequestMethod.POST)
    public DataGirdView getDeptChildrenCountById(Integer id){
        Integer count=deptService.queryDeptChildrenCountById(id);
        return new DataGirdView(count);
    }

    @RequiresPermissions("dept:delete")
    @RequestMapping(value = "delDept",method = RequestMethod.POST)
    public ResultObj delDept(Integer id){
        if(null == id){
            return ResultObj.DELETE_WRONG;
        }
        try {
            deptService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_FAIL;
        }
    }


}
