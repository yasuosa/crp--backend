package com.rpy.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rpy.business.domain.Provider;
import com.rpy.business.mapper.ProviderMapper;
import com.rpy.business.service.ProviderService;
import com.rpy.business.vo.GoodsVo;
import com.rpy.system.common.DataGirdView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rpy.business.mapper.GoodsMapper;
import com.rpy.business.domain.Goods;
import com.rpy.business.service.GoodsService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService{


    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private ProviderService providerService;


    @Override
    public DataGirdView queryAllGoods(GoodsVo goodsVo) {
        IPage<Goods> page=new Page<>(goodsVo.getPage(),goodsVo.getLimit());
        QueryWrapper<Goods> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(null !=goodsVo.getProviderid(),"providerid",goodsVo.getProviderid());
        queryWrapper.eq(null !=goodsVo.getAvailable(),"available",goodsVo.getAvailable());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getGoodsname()),"goodsname",goodsVo.getGoodsname());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getSize()),"size",goodsVo.getSize());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getProductcode()),"productcode",goodsVo.getProductcode());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getDescription()),"description",goodsVo.getDescription());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getPromitcode()),"promitcode",goodsVo.getPromitcode());
        goodsMapper.selectPage(page,queryWrapper);

        List<Goods> records = page.getRecords();
        for (Goods record : records) {
            if(null !=record){
                Provider provider = providerService.getById(record.getProviderid());
                record.setProvidername(provider.getProvidername());
            }
        }
        return new DataGirdView(page.getTotal(), records);
    }

    @Override
    @CachePut(cacheNames = "com.rpy.business.service.impl.GoodsServiceImpl",key = "#result.id")
    public Goods saveGoods(Goods goods) {
        super.save(goods);
        return goods;
    }

    @Override
    @CachePut(cacheNames = "com.rpy.business.service.impl.GoodsServiceImpl",key = "#result.id")
    public Goods updateGoods(Goods goods) {
        updateById(goods);
        return goods;
    }

    @Override
    public List<Goods> queryGoodsByProviderId(Integer providerid) {
        QueryWrapper<Goods> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("providerid",providerid);
        return  goodsMapper.selectList(queryWrapper);
    }

    @Override
    @CachePut(cacheNames = "com.rpy.business.service.impl.GoodsServiceImpl",key = "#result.id")
    public Goods getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    @CacheEvict(cacheNames = "com.rpy.business.service.impl.GoodsServiceImpl",key = "#id")
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        return removeByIds(idList);
    }


}
