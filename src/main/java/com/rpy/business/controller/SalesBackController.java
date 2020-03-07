package com.rpy.business.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rpy.business.domain.Salesback;
import com.rpy.business.service.SalesbackService;
import com.rpy.business.vo.SalesbackVo;
import com.rpy.system.common.Constant;
import com.rpy.system.common.DataGirdView;
import com.rpy.system.common.ResultObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther 任鹏宇
 * @Date 2020/2/26
 */
@RestController
@RequestMapping("salesback")
public class SalesBackController {

    @Autowired
    private SalesbackService salesbackService;


    /**
     * 查询所有
     * @param salesbackVo
     * @return
     */
    @RequestMapping(value = "loadAllSalesback",method = RequestMethod.GET)
    public DataGirdView loadAllSalesback(SalesbackVo salesbackVo){
        return salesbackService.queryAllSalesback(salesbackVo);
    }

    /**
     * 添加
     * @param salesback
     * @return
     */
    @RequestMapping(value = "addSalesback", method = RequestMethod.POST)
    public ResultObj addSalesback(Salesback salesback){
        try {
            salesbackService.saveSalesback(salesback);
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
    @RequestMapping(value = "delSalesback", method = RequestMethod.POST)
    public ResultObj delSalesback(Integer id){
        try {
            if(null == id){
                return ResultObj.DELETE_WRONG;
            }
            salesbackService.removeById(id);
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
    @RequestMapping(value = "updateSalesback", method = RequestMethod.POST)
    public ResultObj updateSalesback(Salesback salesback){
        try {
            if(null==salesback.getId()){
                return ResultObj.UPDATE_WRONG;
            }
            salesbackService.updateSalesback(salesback);
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
    @RequestMapping(value = "batchDelSalesback", method = RequestMethod.POST)
    public ResultObj batchDelSalesback(Integer[] ids){
        if(null == ids || ids.length<=1){
            return ResultObj.DELETE_WRONG;
        }
        try {
            for (Integer id : ids) {
                salesbackService.removeById(id);
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
    @RequestMapping(value = "getAllAvailableSalesback",method = RequestMethod.GET)
    public Object getAllAvailableSalesback(){
        QueryWrapper<Salesback> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("available",Constant.AVAILABLE_TRUE);
        return new DataGirdView(salesbackService.list(queryWrapper));
    }

}
