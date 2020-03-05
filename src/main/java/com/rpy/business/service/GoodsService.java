package com.rpy.business.service;

import com.rpy.business.domain.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rpy.business.vo.GoodsVo;
import com.rpy.system.common.DataGirdView;

import java.util.List;

public interface GoodsService extends IService<Goods>{


    DataGirdView queryAllGoods(GoodsVo goodsVo);

    Goods saveGoods(Goods goods);

    Goods updateGoods(Goods goods);

    List<Goods> queryGoodsByProviderId(Integer providerid);

}
