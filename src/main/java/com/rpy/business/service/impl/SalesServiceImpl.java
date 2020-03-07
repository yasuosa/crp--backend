package com.rpy.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rpy.business.domain.*;
import com.rpy.business.mapper.SalesMapper;
import com.rpy.business.service.CustomerService;
import com.rpy.business.service.GoodsService;
import com.rpy.business.service.ProviderService;
import com.rpy.business.vo.SalesVo;
import com.rpy.system.common.DataGirdView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rpy.business.mapper.SalesMapper;
import com.rpy.business.domain.Sales;
import com.rpy.business.service.SalesService;
@Service
public class SalesServiceImpl extends ServiceImpl<SalesMapper, Sales> implements SalesService{

    @Autowired
    private SalesMapper salesMapper;

    @Autowired
    private CustomerService customerService;


    @Autowired
    private GoodsService goodsService;


    @Override
    public Sales saveSales(Sales sales) {
        super.save(sales);
        //跟新库存
        Integer goodsid = sales.getGoodsid();
        Goods goods = goodsService.getById(goodsid);
        goods.setNumber(goods.getNumber()-sales.getNumber());
        goodsService.updateGoods(goods);
        return sales;
    }

    @Override
    public Sales updateSales(Sales sales) {
        Sales oldObj=salesMapper.selectById(sales.getId());

        Goods goods = goodsService.getById(oldObj.getGoodsid());
        goods.setNumber(goods.getNumber()+oldObj.getNumber()-sales.getNumber());
        goodsService.updateGoods(goods);
        super.updateById(sales);
        return sales;
    }

    @Override
    public DataGirdView queryAllSales(SalesVo salesVo) {
        IPage<Sales> page=new Page<>(salesVo.getPage(),salesVo.getLimit());
        QueryWrapper<Sales> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(salesVo.getGoodsid()!=null,"goodsid",salesVo.getGoodsid());
        queryWrapper.eq(salesVo.getCustomerid()!=null && !salesVo.getCustomerid().equals(""),"customerid",salesVo.getCustomerid());
        queryWrapper.ge(null!=salesVo.getStartTime(),"salestime",salesVo.getStartTime());
        queryWrapper.le(null!=salesVo.getStartTime(),"salestime",salesVo.getEndTime());
        queryWrapper.orderByDesc("salestime");
        salesMapper.selectPage(page,queryWrapper);
        List<Sales> records = page.getRecords();
        for (Sales record : records) {
            if(null != record.getGoodsid()){
                Goods goods = goodsService.getById(record.getGoodsid());
                record.setGoodsname(goods.getGoodsname());
                record.setSize(goods.getSize());
            }
            if(null != record.getCustomerid()){
                Customer customer = customerService.getById(record.getCustomerid());
                record.setCustomername(customer.getCustomername());
            }
        }
        return new DataGirdView(page.getTotal(),page.getRecords());
    }

    @Override
    public boolean removeById(Serializable id) {
        Sales sales=salesMapper.selectById(id);
        Goods goods=goodsService.getById(sales.getGoodsid());
        goods.setNumber(goods.getNumber()+sales.getNumber());
        goodsService.updateGoods(goods);

        return super.removeById(id);
    }
}
