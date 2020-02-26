package com.rpy.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rpy.system.common.ActiveUser;
import com.rpy.system.common.Constant;
import com.rpy.system.common.MenuTreeNode;
import com.rpy.system.domain.Menu;
import com.rpy.system.domain.User;
import com.rpy.system.service.MenuService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @Auther 任鹏宇
 * @Date 2020/2/24
 */

@RequestMapping("index")
@RestController
public class IndexController {


    @Autowired
    private MenuService menuService;


    /**
     * 加载所有菜单
     */
    @RequestMapping(value = "loadIndexMenu",method = RequestMethod.GET)
    public Object loadIndexMenu(){
        //得到当前登陆的用户
        Subject subject = SecurityUtils.getSubject();
        ActiveUser activeUser  = (ActiveUser) subject.getPrincipal();
        User user = activeUser.getUser();
        if(null == user){
            return null;
        }
        List<Menu> menus=null;
        if (user.getType().equals(Constant.USER_TYPE_SURPER)) { //超级管理员
            menus=menuService.queryAllMenuForList();
        }else {
            menus=menuService.queryMenuForListByUserId(user.getId());
        }
        List<MenuTreeNode> treeNodes=new ArrayList<>();
        for (Menu m : menus) {
            treeNodes.add(new MenuTreeNode(m.getId(), m.getPid(), m.getTitle(), m.getHref(), m.getIcon(), m.getSpread()==Constant.SPREAD_TRUE, m.getTarget(),m.getTypecode()));
        }
        List<MenuTreeNode> nodes = MenuTreeNode.MenuTreeNodeBuilder.build(treeNodes, Constant.TOP);
        Map<String,Object> map=new HashMap<>();
        for (MenuTreeNode node : nodes) {
            map.put(node.getTyepcode(),node);
        }
        return map;

    }
}
