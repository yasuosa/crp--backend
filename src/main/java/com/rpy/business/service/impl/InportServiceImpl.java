package com.rpy.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rpy.business.domain.Goods;
import com.rpy.business.domain.Provider;
import com.rpy.business.service.GoodsService;
import com.rpy.business.service.ProviderService;
import com.rpy.business.vo.InportVo;
import com.rpy.system.common.DataGirdView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rpy.business.mapper.InportMapper;
import com.rpy.business.domain.Inport;
import com.rpy.business.service.InportService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class InportServiceImpl extends ServiceImpl<InportMapper, Inport> implements InportService{

    @Autowired
    private InportMapper inportMapper;

    @Autowired
    private ProviderService providerService;


    @Autowired
    private GoodsService goodsService;


    @Override
    public Inport saveInport(Inport inport) {
        super.save(inport);
        //跟新库存
        Integer goodsid = inport.getGoodsid();
        Goods goods = goodsService.getById(goodsid);
        goods.setNumber(goods.getNumber()+inport.getNumber());
        goodsService.updateGoods(goods);
        return inport;
    }

    @Override
    public Inport updateInport(Inport inport) {
        Inport oldObj=inportMapper.selectById(inport.getId());

        Goods goods = goodsService.getById(oldObj.getGoodsid());
        goods.setNumber(goods.getNumber()-oldObj.getNumber()+inport.getNumber());
        goodsService.updateGoods(goods);
        super.updateById(inport);
        return inport;
    }

    @Override
    public DataGirdView queryAllInport(InportVo inportVo) {
        IPage<Inport> page=new Page<>(inportVo.getPage(),inportVo.getLimit());
        QueryWrapper<Inport> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(inportVo.getGoodsid()!=null,"goodsid",inportVo.getGoodsid());
        queryWrapper.eq(inportVo.getProviderid()!=null && !inportVo.getProviderid().equals(""),"providerid",inportVo.getProviderid());
        queryWrapper.ge(null!=inportVo.getStartTime(),"inporttime",inportVo.getStartTime());
        queryWrapper.le(null!=inportVo.getStartTime(),"inporttime",inportVo.getEndTime());
        queryWrapper.orderByDesc("inporttime");
        inportMapper.selectPage(page,queryWrapper);
        List<Inport> records = page.getRecords();
        for (Inport record : records) {
            if(null != record.getGoodsid()){
                Goods goods = goodsService.getById(record.getGoodsid());
                record.setGoodsname(goods.getGoodsname());
                record.setSize(goods.getSize());
            }
            if(null != record.getProviderid()){
                Provider provider = providerService.getById(record.getProviderid());
                record.setProvidername(provider.getProvidername());
            }
        }
        return new DataGirdView(page.getTotal(),page.getRecords());
    }

    @Override
    public boolean removeById(Serializable id) {
        Inport inport=inportMapper.selectById(id);
        Goods goods=goodsService.getById(inport.getGoodsid());
        goods.setNumber(goods.getNumber()-inport.getNumber());
        goodsService.updateGoods(goods);

        return super.removeById(id);
    }
}
