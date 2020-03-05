package com.rpy.system.service;

import com.rpy.system.common.DataGirdView;
import com.rpy.system.domain.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rpy.system.vo.RoleVo;

import java.util.List;

public interface RoleService extends IService<Role>{


    DataGirdView queryAllRole(RoleVo roleVo);

    Role saveRole(Role role);

    Role updateRole(Role role);

    List<Integer> queryMenuIdsByRid(Integer rid);

    void saveRoleMenu(Integer rid, Integer[] mids);

    DataGirdView loadAllAvailableRoleNoPage(Integer userid);

    List<String> queryRoleNamesByUserId(Integer id);

}
