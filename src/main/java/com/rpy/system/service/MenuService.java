package com.rpy.system.service;

import com.rpy.system.common.DataGirdView;
import com.rpy.system.domain.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rpy.system.vo.MenuVo;

import java.util.List;

public interface MenuService extends IService<Menu>{


    /**
     * 全查询菜单
     * @return
     */
    List<Menu> queryAllMenuForList();

    /**
     * 根据用户id查询菜单
     * @param id
     * @return
     */
    List<Menu> queryMenuForListByUserId(Integer id);

    Menu updateMenu(Menu menu);

    Integer queryMenuChildrenCountById(Integer id);

    Integer queryMenuMaxOrderNum();

    Menu saveMenu(Menu menu);

    DataGirdView queryAllMenu(MenuVo menuVo);

    List<String> queryPermissionCodeByUserId(Integer id);

}
