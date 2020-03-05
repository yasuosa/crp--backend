package com.rpy.system.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.rpy.system.common.ActiveUser;
import com.rpy.system.common.Constant;
import com.rpy.system.domain.User;
import com.rpy.system.service.MenuService;
import com.rpy.system.service.RoleService;
import com.rpy.system.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;


/**
 * @Auther 任鹏宇
 * @Date 2020/2/24
 */
public class UserRealm extends AuthorizingRealm {


    @Autowired
    @Lazy //只有使用的时候才加载
    private UserService userService;

    @Autowired
    @Lazy
    private RoleService roleService;

    @Autowired
    @Lazy
    private MenuService menuService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("loginname", token.getPrincipal().toString());
        User user = userService.getOne(queryWrapper);
        if (null != user) {
            ActiveUser activeUser=new ActiveUser();
            activeUser.setUser(user);
            //根据用户id查询角色的集合
            List<String> roles=this.roleService.queryRoleNamesByUserId(user.getId());
            //根据角色id查询权限编码
            List<String> permissions=this.menuService.queryPermissionCodeByUserId(user.getId());

            activeUser.setRoles(roles);
            activeUser.setPermissions(permissions);
            //加入salt
            ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activeUser, user.getPwd(), credentialsSalt,
                    this.getName());
            return info;
        }
        return null;
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        AuthorizationInfo info=new SimpleAuthorizationInfo();
        ActiveUser activeUser = (ActiveUser) principals.getPrimaryPrincipal();
        List<String> roles = activeUser.getRoles();
        List<String> permissions = activeUser.getPermissions();
        User user = activeUser.getUser();
        if(user.getType().equals(Constant.USER_TYPE_SURPER)){
            ((SimpleAuthorizationInfo) info).addStringPermission("*:*");
        }else {
            if(null !=roles && roles.size()>0){
                ((SimpleAuthorizationInfo) info).addRoles(roles);
            }
            if(null !=permissions && permissions.size()>0){
                ((SimpleAuthorizationInfo) info).addStringPermissions(permissions);
            }
        }
        return info;
    }


    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }
}
