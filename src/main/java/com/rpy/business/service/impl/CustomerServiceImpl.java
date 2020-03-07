package com.rpy.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rpy.business.vo.CustomerVo;
import com.rpy.system.common.Constant;
import com.rpy.system.common.DataGirdView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rpy.business.mapper.CustomerMapper;
import com.rpy.business.domain.Customer;
import com.rpy.business.service.CustomerService;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

@Service
@Transactional(rollbackFor = Exception.class)
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService{


    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public DataGirdView queryAllCustomer(CustomerVo customerVo) {
        IPage<Customer> page=new Page<>(customerVo.getPage(),customerVo.getLimit());
        QueryWrapper<Customer> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(customerVo.getAvailable()!=null,"available",customerVo.getAvailable());
        queryWrapper.like(StringUtils.isNotBlank(customerVo.getCustomername()),"providername",customerVo.getCustomername());
        queryWrapper.like(StringUtils.isNotBlank(customerVo.getConnectionperson()),"connectionperson",customerVo.getConnectionperson());
        if(StringUtils.isNotBlank(customerVo.getPhone())) {
            queryWrapper.and(new Consumer<QueryWrapper<Customer>>() {
                @Override
                public void accept(QueryWrapper<Customer> customerQueryWrapper) {
                    customerQueryWrapper.like(StringUtils.isNotBlank(customerVo.getPhone()), "phone", customerVo.getPhone())
                            .or()
                            .like(StringUtils.isNotBlank(customerVo.getPhone()), "telephone", customerVo.getPhone());
                }
            });
        }
        customerMapper.selectPage(page,queryWrapper);
        return new DataGirdView(page.getTotal(),page.getRecords());
    }

    @Override
    @CachePut(cacheNames = "com.rpy.business.service.impl.CustomerServiceImpl" ,key = "#result.id")
    public Customer saveCustomer(Customer customer) {
        super.save(customer);
        return customer;
    }

    @Override
    @CachePut(cacheNames = "com.rpy.business.service.impl.CustomerServiceImpl" ,key = "#result.id")
    public Customer updateCustomer(Customer customer) {
        Customer c=customerMapper.selectById(customer.getId());
        BeanUtil.copyProperties(customer,c, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        super.updateById(c);
        return c;
    }

    @Override
    public DataGirdView getAllAvailableCustomer() {
        QueryWrapper<Customer> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("available", Constant.AVAILABLE_TRUE);
        List<Customer> list = this.list(queryWrapper);
        return new DataGirdView(list);
    }

    @Override
    @CacheEvict(cacheNames = "com.rpy.business.service.impl.CustomerServiceImpl" ,key = "#id")
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        return super.removeByIds(idList);
    }

    @Override
    @CachePut(cacheNames = "com.rpy.business.service.impl.CustomerServiceImpl" ,key = "#result.id")
    public Customer getById(Serializable id) {
        return super.getById(id);
    }
}
