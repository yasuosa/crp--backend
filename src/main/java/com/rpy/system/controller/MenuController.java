package com.rpy.system.controller;

import com.rpy.system.common.Constant;
import com.rpy.system.common.DataGirdView;
import com.rpy.system.common.ResultObj;
import com.rpy.system.domain.Menu;
import com.rpy.system.service.MenuService;
import com.rpy.system.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther 任鹏宇
 * @Date 2020/2/27
 */
@RestController
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private MenuService menuService;


    @RequestMapping(value = "loadAllMenu" , method = RequestMethod.GET)
    public DataGirdView loadAllMenu(MenuVo menuVo){
        return this.menuService.queryAllMenu(menuVo);
    }

    @RequestMapping(value = "loadMenu" , method = RequestMethod.GET)
    public DataGirdView loadMenu(){
        List<Menu> menus = this.menuService.queryAllMenuForList();
        return new DataGirdView(Long.valueOf(menus.size()),menus);
    }


    @RequestMapping(value = "queryMenuMaxOrderNum",method = RequestMethod.GET)
    public Object queryMenuMaxOrderNum(){
        Integer maxVal=menuService.queryMenuMaxOrderNum();
        return new DataGirdView(maxVal+1);
    }


    @RequestMapping(value = "addMenu",method = RequestMethod.POST)
    public ResultObj addMenu(Menu menu){
        try {
            if(menu.getType().equals(Constant.MENU_TYPE_TOP)){
                menu.setPid(0);
            }
            menu.setSpread(Constant.SPREAD_FALSE);
            menu.setAvailable(Constant.AVAILABLE_TRUE);
            menuService.saveMenu(menu);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_FAIL;
        }
    }

    @RequestMapping(value = "getOneMenuById" ,method = RequestMethod.GET)
    public DataGirdView getOneMenuById(Integer id){
        Menu menu = menuService.getById(id);
        return new DataGirdView(menu);
    }


    @RequestMapping(value = "updateMenu",method = RequestMethod.POST)
    public ResultObj updateMenu(Menu menu){
        if(null == menu || menu.getId()==null){
            return ResultObj.UPDATE_WRONG;
        }
        try {
            menuService.updateMenu(menu);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_FAIL;
        }
    }

    @RequestMapping(value = "getMenuChildrenCountById",method = RequestMethod.POST)
    public DataGirdView getMenuChildrenCountById(Integer id){
        Integer count=menuService.queryMenuChildrenCountById(id);
        return new DataGirdView(count);
    }


    @RequestMapping(value = "delMenu",method = RequestMethod.POST)
    public ResultObj delMenu(Integer id){
        if(null == id){
            return ResultObj.DELETE_WRONG;
        }
        try {
            menuService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_FAIL;
        }
    }


}
