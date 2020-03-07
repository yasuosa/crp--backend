package com.rpy.business.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rpy.business.domain.Outport;
import com.rpy.business.service.OutportService;
import com.rpy.business.vo.OutportVo;
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
@RequestMapping("outport")
public class OutportController {

    @Autowired
    private OutportService outportService;


    /**
     * 查询所有
     * @param outportVo
     * @return
     */
    @RequestMapping(value = "loadAllOutport",method = RequestMethod.GET)
    public DataGirdView loadAllOutport(OutportVo outportVo){
        return outportService.queryAllOutport(outportVo);
    }

    /**
     * 添加
     * @param outport
     * @return
     */
    @RequestMapping(value = "addOutport", method = RequestMethod.POST)
    public ResultObj addOutport(Outport outport){
        try {
            outportService.saveOutport(outport);
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
    @RequestMapping(value = "delOutport", method = RequestMethod.POST)
    public ResultObj delOutport(Integer id){
        try {
            if(null == id){
                return ResultObj.DELETE_WRONG;
            }
            outportService.removeById(id);
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
    @RequestMapping(value = "updateOutport", method = RequestMethod.POST)
    public ResultObj updateOutport(Outport outport){
        try {
            if(null==outport.getId()){
                return ResultObj.UPDATE_WRONG;
            }
            outportService.updateOutport(outport);
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
    @RequestMapping(value = "batchDelOutport", method = RequestMethod.POST)
    public ResultObj batchDelOutport(Integer[] ids){
        if(null == ids || ids.length<=1){
            return ResultObj.DELETE_WRONG;
        }
        try {
            for (Integer id : ids) {
                outportService.removeById(id);
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
    @RequestMapping(value = "getAllAvailableOutport",method = RequestMethod.GET)
    public Object getAllAvailableOutport(){
        QueryWrapper<Outport> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("available",Constant.AVAILABLE_TRUE);
        return new DataGirdView(outportService.list(queryWrapper));
    }

}
