package com.rpy;

import com.rpy.system.domain.Menu;
import com.rpy.system.service.MenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MenuUtils {

    @Autowired
    private MenuService menuService;


    @Test
    void contextLoads() {
//        menuService.save(new Menu(1,0,"topmenu","business","业务管理","fa fa-diamond","", null,0,1,1));
//        menuService.save(new Menu(2,0,"topmenu","system","系统管理","fa fa-gear","", null,0,2,1));
//
//        /**业务管理**/
//        menuService.save(new Menu(3,1,"leftmenu","business","基础数据管理","fa fa-database","", null,0,3,1));
//        menuService.save(new Menu(4,1,"leftmenu","business","进货管理","fa fa-shopping-cart","", null,0,4,1));
//        menuService.save(new Menu(5,1,"leftmenu","business","销售管理","fa fa-area-chart","", null,0,5,1));
//
//        /**基础数据管理**/
//        menuService.save(new Menu(6,3,"leftmenu","business","客户管理","fa fa-users","", null,0,6,1));
//        menuService.save(new Menu(7,3,"leftmenu","business","供应商管理","fa fa-user-secret","", null,0,7,1));
//        menuService.save(new Menu(8,3,"leftmenu","business","商品管理","fa fa-info-circle","", null,0,8,1));
//
//        /**进货管理**/
//        menuService.save(new Menu(9,4,"leftmenu","business","商品进货","fa fa-angle-double-down","", null,0,9,1));
//        menuService.save(new Menu(10,4,"leftmenu","business","商品退货","fa fa-angle-double-up","", null,0,10,1));
//
//        /**销售管理**/
//        menuService.save(new Menu(11,5,"leftmenu","business","商品销售","fa fa-line-chart","", null,0,11,1));
//        menuService.save(new Menu(12,5,"leftmenu","business","销售退货","fa fa-long-arrow-down","", null ,0,12,1));
//
//        /**系统管理**/
//        menuService.save(new Menu(13,2,"leftmenu","system","系统管理","fa fa-cog","", null,0,13,1));
//        menuService.save(new Menu(14,2,"leftmenu","system","其他管理","fa fa-spinner","", null,0,14,1));
//
//        /**系统管理left**/
//        menuService.save(new Menu(15,13,"leftmenu","system","部门管理","fa fa-user-plus","", null,0,15,1));
//        menuService.save(new Menu(16,13,"leftmenu","system","菜单管理","fa fa-tags","", null,0,16,1));
//        menuService.save(new Menu(17,13,"leftmenu","system","角色管理","fa fa-street-view","", null,0,17,1));
//        menuService.save(new Menu(18,13,"leftmenu","system","用户管理","fa fa-male","", null,0,18,1));
//
//        /**其他管理left**/
//        menuService.save(new Menu(19,14,"leftmenu","system","登陆日志","fa fa-certificate","", null,0,19,1));
//        menuService.save(new Menu(20,14,"leftmenu","system","数据源监控","fa fa-bolt","", null,0,20,1));
//        menuService.save(new Menu(21,14,"leftmenu","system","系统公告","fa fa-calendar-o","", null,0,21,1));
//        menuService.save(new Menu(22,14,"leftmenu","system","图标管理","fa fa-bug","", null,0,22,1));

        System.out.println("初始化成功");

    }

    @Test
    void initPermission(){
        menuService.save(new Menu(23,6,"permission","customer:query","客户查询",23,1));
        menuService.save(new Menu(24,6,"permission","customer:add","客户添加",24,1));
        menuService.save(new Menu(25,6,"permission","customer:delete","客户删除",25,1));
        menuService.save(new Menu(26,6,"permission","customer:update","客户更新",26,1));
    }

}
