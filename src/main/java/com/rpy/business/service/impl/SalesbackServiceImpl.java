package com.rpy.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rpy.business.domain.*;
import com.rpy.business.mapper.SalesMapper;
import com.rpy.business.service.CustomerService;
import com.rpy.business.service.GoodsService;
import com.rpy.business.vo.SalesbackVo;
import com.rpy.system.common.DataGirdView;
import com.rpy.system.utils.SessionDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rpy.business.mapper.SalesbackMapper;
import com.rpy.business.service.SalesbackService;
@Service
public class SalesbackServiceImpl extends ServiceImpl<SalesbackMapper, Salesback> implements SalesbackService{


    @Autowired
    private SalesMapper salesMapper;

    @Autowired
    private SalesbackMapper salesbackMapper;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private GoodsService goodsService;

    @Override
    public DataGirdView queryAllSalesback(SalesbackVo salesbackVo) {
        IPage<Salesback> page=new Page<>(salesbackVo.getPage(),salesbackVo.getLimit());
        QueryWrapper<Salesback> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(salesbackVo.getGoodsid()!=null,"goodsid",salesbackVo.getGoodsid());
        queryWrapper.eq(salesbackVo.getProviderid()!=null && !salesbackVo.getProviderid().equals(""),"providerid",salesbackVo.getProviderid());
        queryWrapper.ge(null!=salesbackVo.getStartTime(),"salesbacktime",salesbackVo.getStartTime());
        queryWrapper.le(null!=salesbackVo.getStartTime(),"salesbacktime",salesbackVo.getEndTime());
        queryWrapper.orderByDesc("salesbacktime");
        salesbackMapper.selectPage(page,queryWrapper);
        List<Salesback> records = page.getRecords();
        for (Salesback record : records) {
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
    public Salesback saveSalesback(Salesback salesback) {
        Integer salesid = salesback.getSalesid();
        Sales sales = salesMapper.selectById(salesid);
        salesback.setGoodsid(sales.getGoodsid());
        salesback.setPaytype(sales.getPaytype());
        salesback.setOperateperson(SessionDataUtils.getUser().getName());
        salesback.setSalesbacktime(new Date());
        salesback.setCustomerid(sales.getCustomerid());
        salesback.setSalebackprice(sales.getSaleprice());
        salesbackMapper.insert(salesback);
        //增加库存
        Goods goods = goodsService.getById(sales.getGoodsid());
        goods.setNumber(goods.getNumber()+salesback.getNumber());
        this.goodsService.updateGoods(goods);

        sales.setNumber(sales.getNumber()-salesback.getNumber());
        salesMapper.updateById(sales);
        return salesback;
    }

    @Override
    public Salesback updateSalesback(Salesback salesback) {
        return null;
    }
}
