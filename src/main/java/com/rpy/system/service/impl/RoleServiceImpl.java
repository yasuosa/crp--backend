package com.rpy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rpy.system.common.DataGirdView;
import com.rpy.system.vo.RoleVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rpy.system.domain.Role;
import com.rpy.system.mapper.RoleMapper;
import com.rpy.system.service.RoleService;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService{


    @Autowired
    private RoleMapper roleMapper;

    @Override
    public DataGirdView queryAllRole(RoleVo roleVo) {
        QueryWrapper<Role> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(roleVo.getAvailable()!=null,"available",roleVo.getAvailable());
        queryWrapper.like(StringUtils.isNotBlank(roleVo.getName()),"name",roleVo.getName());
        queryWrapper.like(StringUtils.isNotBlank(roleVo.getRemark()),"remark",roleVo.getRemark());
        IPage<Role> page=new Page<>(roleVo.getPage(),roleVo.getLimit());
        roleMapper.selectPage(page,queryWrapper);

        return new DataGirdView(page.getTotal(),page.getRecords());
    }

    @Override
    public Role saveRole(Role role) {
        roleMapper.insert(role);
        return role;
    }

    @Override
    public Role updateRole(Role role) {
        roleMapper.updateById(role);
        return role;
    }

    @Override
    public List<Integer> queryMenuIdsByRid(Integer rid) {
        return roleMapper.queryMenuIdsByRid(rid);
    }

    @Override
    public void saveRoleMenu(Integer rid, Integer[] mids) {
        //先删除角色菜单的关系
        roleMapper.deleteRoleMenuByRid(rid);
        if(null!=mids && mids.length>0) {
            roleMapper.saveRoleMenu(rid, mids);
        }
    }

    @Override
    public DataGirdView loadAllAvailableRoleNoPage(Integer userid) {
        List<Role> roles = super.list();
        List<Integer> rids = roleMapper.queryRoleIdByUid(userid);
        for (Role role : roles) {
            Integer rid = role.getId();
            if(rids.contains(rid)){
                role.setLAY_CHECKED(true);
            }
        }
        return new DataGirdView(roles);
    }

    @Override
    public boolean removeById(Serializable id) {
        //根据角色id删除菜单关系
        roleMapper.deleteRoleMenuByRid(id);
        //根据角色id删除用户关系
        roleMapper.deleteUserMenuByRid(id);
        return super.removeById(id);
    }




}
