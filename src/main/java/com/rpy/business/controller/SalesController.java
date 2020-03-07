package com.rpy.business.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rpy.business.domain.Sales;
import com.rpy.business.service.SalesService;
import com.rpy.business.vo.SalesVo;
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
@RequestMapping("sales")
public class SalesController {

    @Autowired
    private SalesService salesService;


    /**
     * 查询所有
     * @param salesVo
     * @return
     */
    @RequestMapping(value = "loadAllSales",method = RequestMethod.GET)
    public DataGirdView loadAllSales(SalesVo salesVo){
        return salesService.queryAllSales(salesVo);
    }

    /**
     * 添加
     * @param sales
     * @return
     */
    @RequestMapping(value = "addSales", method = RequestMethod.POST)
    public ResultObj addSales(Sales sales){
        try {
            String name = SessionDataUtils.getUser().getName();
            sales.setOperateperson(name);
            sales.setSalestime(new Date());
            salesService.saveSales(sales);
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
    @RequestMapping(value = "delSales", method = RequestMethod.POST)
    public ResultObj delSales(Integer id){
        try {
            if(null == id){
                return ResultObj.DELETE_WRONG;
            }
            salesService.removeById(id);
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
    @RequestMapping(value = "updateSales", method = RequestMethod.POST)
    public ResultObj updateSales(Sales sales){
        try {
            if(null==sales.getId()){
                return ResultObj.UPDATE_WRONG;
            }
            salesService.updateSales(sales);
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
    @RequestMapping(value = "batchDelSales", method = RequestMethod.POST)
    public ResultObj batchDelales(Integer[] ids){
        if(null == ids || ids.length<=1){
            return ResultObj.DELETE_WRONG;
        }
        try {
            for (Integer id : ids) {
                salesService.removeById(id);
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
    @RequestMapping(value = "getAllAvailableSales",method = RequestMethod.GET)
    public Object getAllAvailableSales(){
        QueryWrapper<Sales> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("available",Constant.AVAILABLE_TRUE);
        return new DataGirdView(salesService.list(queryWrapper));
    }

}
