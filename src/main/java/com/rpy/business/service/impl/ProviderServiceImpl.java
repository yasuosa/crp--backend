package com.rpy.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rpy.business.domain.Provider;
import com.rpy.business.vo.ProviderVo;
import com.rpy.system.common.DataGirdView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rpy.business.domain.Provider;
import com.rpy.business.mapper.ProviderMapper;
import com.rpy.business.service.ProviderService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Serializable;
import java.util.function.Consumer;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProviderServiceImpl extends ServiceImpl<ProviderMapper, Provider> implements ProviderService{

    
    @Autowired
    private ProviderMapper providerMapper;
    
    
    @Override
    public DataGirdView queryAllProvider(ProviderVo providerVo) {
        IPage<Provider> page=new Page<>(providerVo.getPage(),providerVo.getLimit());
        QueryWrapper<Provider> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(providerVo.getAvailable()!=null,"available",providerVo.getAvailable());
        queryWrapper.like(StringUtils.isNotBlank(providerVo.getProvidername()),"providername",providerVo.getProvidername());
        queryWrapper.like(StringUtils.isNotBlank(providerVo.getConnectionperson()),"connectionperson",providerVo.getConnectionperson());
        if(StringUtils.isNotBlank(providerVo.getPhone())) {
            queryWrapper.and(new Consumer<QueryWrapper<Provider>>() {
                @Override
                public void accept(QueryWrapper<Provider> providerQueryWrapper) {
                    providerQueryWrapper.like(StringUtils.isNotBlank(providerVo.getPhone()), "phone", providerVo.getPhone())
                            .or()
                            .like(StringUtils.isNotBlank(providerVo.getPhone()), "telephone", providerVo.getPhone());
                }
            });
        }
        providerMapper.selectPage(page,queryWrapper);
        return new DataGirdView(page.getTotal(),page.getRecords());
    }

    @CachePut(cacheNames = "com.rpy.business.service.impl.ProviderServiceImpl",key="#result.id")
    @Override
    public Provider saveProvider(Provider provider) {
        super.save(provider);
        return provider;
    }

    @CachePut(cacheNames = "com.rpy.business.service.impl.ProviderServiceImpl",key="#result.id")
    @Override
    public Provider updateProvider(Provider provider) {
        super.updateById(provider);
        return provider;
    }

    @CachePut(cacheNames = "com.rpy.business.service.impl.ProviderServiceImpl",key="#id")
    @Override
    public Provider getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    @CacheEvict(cacheNames = "com.rpy.business.service.impl.ProviderServiceImpl",key="#id")
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }
}
