package com.rpy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rpy.system.common.Constant;
import com.rpy.system.common.DataGirdView;
import com.rpy.system.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.function.Consumer;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rpy.system.domain.Menu;
import com.rpy.system.mapper.MenuMapper;
import com.rpy.system.service.MenuService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService{


    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> queryAllMenuForList() {
        QueryWrapper<Menu> qw=new QueryWrapper<>();
        qw.eq("available", Constant.AVAILABLE_TRUE);
        qw.and(new Consumer<QueryWrapper<Menu>>() {
            @Override
            public void accept(QueryWrapper<Menu> menuQueryWrapper) {
                menuQueryWrapper.eq("type",Constant.MENU_TYPE_TOP)
                        .or().eq("type",Constant.MENU_TYPE_LEFT);
            }
        });
        qw.orderByAsc("ordernum");
        return menuMapper.selectList(qw);
    }

    @Override
    public List<Menu> queryMenuForListByUserId(Integer id) {
        QueryWrapper<Menu> qw=new QueryWrapper<>();
        qw.eq("available", Constant.AVAILABLE_TRUE);
        qw.and(new Consumer<QueryWrapper<Menu>>() {
            @Override
            public void accept(QueryWrapper<Menu> menuQueryWrapper) {
                menuQueryWrapper.eq("type",Constant.MENU_TYPE_TOP)
                        .or().eq("type",Constant.MENU_TYPE_LEFT);
            }
        });
        qw.orderByAsc("ordernum");
        return menuMapper.selectList(qw);
    }


    @Override
    public Menu updateMenu(Menu menu) {
        menuMapper.updateById(menu);
        return menu;
    }

    @Override
    public Integer queryMenuChildrenCountById(Integer id) {
        return menuMapper.queryMenuChildrenCountById(id);
    }

    @Override
    public Integer queryMenuMaxOrderNum() {
        return this.menuMapper.queryMenuMaxOrderNum();
    }

    @Override
    public Menu saveMenu(Menu menu) {
        menuMapper.insert(menu);
        return menu;
    }

    @Override
    public DataGirdView queryAllMenu(MenuVo menuVo) {
        QueryWrapper<Menu> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(menuVo.getAvailable()!=null,"available",menuVo.getAvailable());
        queryWrapper.orderByAsc("ordernum");
        List<Menu> menus = menuMapper.selectList(queryWrapper);
        return new DataGirdView(Long.valueOf(menus.size()),menus);
    }
}
