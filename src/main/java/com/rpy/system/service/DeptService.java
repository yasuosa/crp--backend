package com.rpy.system.service;

import com.rpy.system.common.DataGirdView;
import com.rpy.system.domain.Dept;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rpy.system.vo.DeptVo;

public interface DeptService extends IService<Dept>{


    DataGirdView queryAllDept(DeptVo deptVo);

    Dept saveDept(Dept dept);

    Integer queryDeptMaxOrderNum();

    Dept updateDept(Dept dept);

    Integer queryDeptChildrenCountById(Integer id);
}
