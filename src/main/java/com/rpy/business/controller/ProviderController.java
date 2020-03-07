package com.rpy.business.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rpy.business.domain.Provider;
import com.rpy.business.service.ProviderService;
import com.rpy.business.vo.ProviderVo;
import com.rpy.system.common.Constant;
import com.rpy.system.common.DataGirdView;
import com.rpy.system.common.ResultObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @Auther 任鹏宇
 * @Date 2020/2/26
 */
@RestController
@RequestMapping("provider")
public class ProviderController {

    @Autowired
    private ProviderService providerService;


    /**
     * 查询所有
     * @param providerVo
     * @return
     */
    @RequestMapping(value = "loadAllProvider",method = RequestMethod.GET)
    public DataGirdView loadAllProvider(ProviderVo providerVo){
        return providerService.queryAllProvider(providerVo);
    }

    /**
     * 添加
     * @param provider
     * @return
     */
    @RequestMapping(value = "addProvider", method = RequestMethod.POST)
    public ResultObj addProvider(Provider provider){
        try {
            provider.setAvailable(Constant.AVAILABLE_TRUE);
            providerService.saveProvider(provider);
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
    @RequestMapping(value = "delProvider", method = RequestMethod.POST)
    public ResultObj delProvider(Integer id){
        try {
            if(null == id){
                return ResultObj.DELETE_WRONG;
            }
            providerService.removeById(id);
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
    @RequestMapping(value = "updateProvider", method = RequestMethod.POST)
    public ResultObj updateProvider(Provider provider){
        try {
            if(null==provider.getId()){
                return ResultObj.UPDATE_WRONG;
            }
            providerService.updateProvider(provider);
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
    @RequestMapping(value = "batchDelProvider", method = RequestMethod.POST)
    public ResultObj batchDelProvider(Integer[] ids){
        if(null == ids || ids.length<=1){
            return ResultObj.DELETE_WRONG;
        }
        try {
            for (Integer id : ids) {
                providerService.removeById(id);
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
    @RequestMapping(value = "getAllAvailableProvider",method = RequestMethod.GET)
    public Object getAllAvailableProvider(){
        QueryWrapper<Provider> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("available",Constant.AVAILABLE_TRUE);
        return new DataGirdView(providerService.list(queryWrapper));
    }

}
