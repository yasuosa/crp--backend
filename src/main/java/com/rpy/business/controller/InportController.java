package com.rpy.business.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rpy.business.domain.Inport;
import com.rpy.business.service.InportService;
import com.rpy.business.vo.InportVo;
import com.rpy.system.common.Constant;
import com.rpy.system.common.DataGirdView;
import com.rpy.system.common.ResultObj;
import com.rpy.system.utils.SessionDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Auther 任鹏宇
 * @Date 2020/2/26
 */
@RestController
@RequestMapping("inport")
public class InportController {

    @Autowired
    private InportService inportService;


    /**
     * 查询所有
     * @param inportVo
     * @return
     */
    @RequestMapping(value = "loadAllInport",method = RequestMethod.GET)
    public DataGirdView loadAllInport(InportVo inportVo){
        return inportService.queryAllInport(inportVo);
    }

    /**
     * 添加
     * @param inport
     * @return
     */
    @RequestMapping(value = "addInport", method = RequestMethod.POST)
    public ResultObj addInport(Inport inport){
        try {
            String name = SessionDataUtils.getUser().getName();
            inport.setOperateperson(name);
            inport.setInporttime(new Date());
            inportService.saveInport(inport);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_FAIL;
        }
    }

    /**
     * 删除
     * @return
     */
    @RequestMapping(value = "delInport", method = RequestMethod.POST)
    public ResultObj delInport(Integer id){
        try {
            if(null == id){
                return ResultObj.DELETE_WRONG;
            }
            inportService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_FAIL;
        }
    }

    /**
     * 修改
     * @return
     */
    @RequestMapping(value = "updateInport", method = RequestMethod.POST)
    public ResultObj updateInport(Inport inport){
        try {
            if(null==inport.getId()){
                return ResultObj.UPDATE_WRONG;
            }
            inportService.updateInport(inport);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_FAIL;
        }
    }



    /**
     * 批量删除
     * @return
     */
    @RequestMapping(value = "batchDelInport", method = RequestMethod.POST)
    public ResultObj batchDelInport(Integer[] ids){
        if(null == ids || ids.length<=1){
            return ResultObj.DELETE_WRONG;
        }
        try {
            for (Integer id : ids) {
                inportService.removeById(id);
            }
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_FAIL;
        }
    }

    /**
     * 查询条件里面的供应商的下拉列表
     * @return
     */
    @RequestMapping(value = "getAllAvailableInport",method = RequestMethod.GET)
    public Object getAllAvailableInport(){
        QueryWrapper<Inport> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("available",Constant.AVAILABLE_TRUE);
        return new DataGirdView(inportService.list(queryWrapper));
    }

}
