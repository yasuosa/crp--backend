package com.rpy;

import com.rpy.system.common.Constant;
import com.rpy.system.common.MenuTreeNode;
import com.rpy.system.domain.Menu;
import com.rpy.system.service.MenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ErpApplicationTests {

    @Autowired
    private MenuService menuService;

    @Test
    void contextLoads() {

    }

}
