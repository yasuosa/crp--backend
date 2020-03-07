package com.rpy.business.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rpy.business.domain.Goods;
import com.rpy.business.domain.Provider;
import com.rpy.business.service.GoodsService;
import com.rpy.business.vo.GoodsVo;
import com.rpy.system.common.Constant;
import com.rpy.system.common.DataGirdView;
import com.rpy.system.common.ResultObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @Auther 任鹏宇
 * @Date 2020/2/26
 */
@RestController
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;


    /**
     * 查询所有
     * @param goodsVo
     * @return
     */
    @RequestMapping(value = "loadAllGoods",method = RequestMethod.GET)
    public DataGirdView loadAllGoods(GoodsVo goodsVo){
        return goodsService.queryAllGoods(goodsVo);
    }

    /**
     * 添加
     * @param goods
     * @return
     */
    @RequestMapping(value = "addGoods", method = RequestMethod.POST)
    public ResultObj addGoods(Goods goods){
        try {
            goods.setAvailable(Constant.AVAILABLE_TRUE);
            goodsService.saveGoods(goods);
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
    @RequestMapping(value = "delGoods", method = RequestMethod.POST)
    public ResultObj addGoods(Integer id){
        try {
            if(null == id){
                return ResultObj.DELETE_WRONG;
            }
            goodsService.removeById(id);
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
    @RequestMapping(value = "updateGoods", method = RequestMethod.POST)
    public ResultObj updateGoods(Goods goods){
        try {
            if(null==goods.getId()){
                return ResultObj.UPDATE_WRONG;
            }
            goodsService.updateGoods(goods);
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
    @RequestMapping(value = "batchDelGoods", method = RequestMethod.POST)
    public ResultObj batchDelGoods(Integer[] ids){
        if(null == ids || ids.length<=1){
            return ResultObj.DELETE_WRONG;
        }
        try {
            for (Integer id : ids) {
                goodsService.removeById(id);
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
    @RequestMapping(value = "getAllAvailableGoods",method = RequestMethod.GET)
    public Object getAllAvailableProvider(){
        QueryWrapper<Goods> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("available",Constant.AVAILABLE_TRUE);
        return new DataGirdView(goodsService.list(queryWrapper));
    }

    /**
     * 查询供应商的商品
     * @param providerid
     * @return
     */
    @RequestMapping(value = "getGoodsByProviderId",method = RequestMethod.GET)
    public Object getGoodsByProviderId(Integer providerid){
        List<Goods> goods=goodsService.queryGoodsByProviderId(providerid);
        return new DataGirdView(goods);
    }

    @RequestMapping(value = "getGoodsByGoodId",method = RequestMethod.GET)
    public Object getGoodsByGoodId(Integer goodsid){
        Goods goods = goodsService.getById(goodsid);
        return new DataGirdView(goods);

    }

}
