package com.rpy.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rpy.system.common.DataGirdView;
import com.rpy.system.vo.DeptVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rpy.system.mapper.DeptMapper;
import com.rpy.system.domain.Dept;
import com.rpy.system.service.DeptService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService{

    @Autowired
    private DeptMapper deptMapper;


    @Override
    public DataGirdView queryAllDept(DeptVo deptVo) {
        QueryWrapper<Dept> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(deptVo.getTitle()),"title",deptVo.getTitle());
        queryWrapper.orderByAsc("ordernum");
        List<Dept> depts = deptMapper.selectList(queryWrapper);

        return new DataGirdView(Long.valueOf(depts.size()),depts);
    }


    @CacheEvict(cacheNames = "com.rpy.system.service.impl.DeptServiceImpl",key = "#id")
    @Override
    public boolean removeById(Serializable id) {
        return false;
    }

    @CachePut(cacheNames = "com.rpy.system.service.impl.DeptServiceImpl",key = "#result.id")
    @Override
    public Dept saveDept(Dept dept) {
        try {
            deptMapper.insert(dept);
            return dept;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Cacheable(cacheNames = "com.rpy.system.service.impl.DeptServiceImpl" ,key = "#id")
    @Override
    public Dept getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public Integer queryDeptMaxOrderNum() {
        return  deptMapper.queryDeptMaxOrderNum();
    }

    @Override
    @CachePut(cacheNames = "com.rpy.system.service.impl.DeptServiceImpl",key = "#result.id")
    public Dept updateDept(Dept dept) {
        try {
            Dept selectById = deptMapper.selectById(dept.getId());
            BeanUtil.copyProperties(dept,selectById, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));

            deptMapper.updateById(selectById);
            return selectById;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer queryDeptChildrenCountById(Integer id) {
        return deptMapper.queryDeptChildrenCountById(id);
    }


}
