package com.rpy.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rpy.business.domain.Goods;
import com.rpy.business.domain.Inport;
import com.rpy.business.domain.Provider;
import com.rpy.business.mapper.InportMapper;
import com.rpy.business.service.GoodsService;
import com.rpy.business.service.ProviderService;
import com.rpy.business.vo.OutportVo;
import com.rpy.system.common.DataGirdView;
import com.rpy.system.utils.SessionDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rpy.business.mapper.OutportMapper;
import com.rpy.business.domain.Outport;
import com.rpy.business.service.OutportService;
@Service
public class OutportServiceImpl extends ServiceImpl<OutportMapper, Outport> implements OutportService{

    @Autowired
    private OutportMapper outportMapper;

    @Autowired
    private InportMapper inportMapper;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private ProviderService providerService;


    @Override
    public DataGirdView queryAllOutport(OutportVo outportVo) {
        IPage<Outport> page=new Page<>(outportVo.getPage(),outportVo.getLimit());
        QueryWrapper<Outport> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(outportVo.getGoodsid()!=null,"goodsid",outportVo.getGoodsid());
        queryWrapper.eq(outportVo.getProviderid()!=null && !outportVo.getProviderid().equals(""),"providerid",outportVo.getProviderid());
        queryWrapper.ge(null!=outportVo.getStartTime(),"outporttime",outportVo.getStartTime());
        queryWrapper.le(null!=outportVo.getStartTime(),"outporttime",outportVo.getEndTime());
        queryWrapper.orderByDesc("outporttime");
        outportMapper.selectPage(page,queryWrapper);
        List<Outport> records = page.getRecords();
        for (Outport record : records) {
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
    public Outport saveOutport(Outport outport) {
        Integer inportid = outport.getInportid();
        Inport inport = inportMapper.selectById(inportid);
        outport.setGoodsid(inport.getGoodsid());
        outport.setPaytype(inport.getPaytype());
        outport.setOperateperson(SessionDataUtils.getUser().getName());
        outport.setOutporttime(new Date());
        outport.setOutportprice(inport.getInportprice());
        outport.setProviderid(inport.getProviderid());
        outportMapper.insert(outport);
        //减少库存
        Goods goods = goodsService.getById(inport.getGoodsid());
        goods.setNumber(goods.getNumber()-outport.getNumber());
        this.goodsService.updateGoods(goods);

        //跟新
        int number = inport.getNumber() - outport.getNumber();
        inport.setNumber(number);
        if(number==0){
            inportMapper.deleteById(inportid);
        }else {
            inportMapper.updateById(inport);
        }
        return outport;
    }

    @Override
    public Outport updateOutport(Outport outport) {
        return null;
    }
}
