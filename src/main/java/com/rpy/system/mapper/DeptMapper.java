package com.rpy.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rpy.system.domain.Dept;

public interface DeptMapper extends BaseMapper<Dept> {
    Integer queryDeptMaxOrderNum();

    Integer queryDeptChildrenCountById(Integer id);
}