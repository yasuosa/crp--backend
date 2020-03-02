package com.rpy.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rpy.system.domain.Role;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {
    void deleteRoleMenuByRid(Serializable id);

    void deleteRoleMenuByMid(Serializable id);

    void deleteUserMenuByRid(Serializable id);

    void deleteUserMenuByUid(Serializable id);

    List<Integer> queryMenuIdsByRid(Integer rid);

    void saveRoleMenu(@Param("rid") Integer rid, @Param("mids") Integer[] mids);

    List<Integer> queryRoleIdByUid(Integer userid);

    void saveUserRole(@Param("uid") Integer uid, @Param("rids") Integer[] rids);
}