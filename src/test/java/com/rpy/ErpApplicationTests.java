package com.rpy;

import com.rpy.system.common.Constant;
import com.rpy.system.common.MenuTreeNode;
import com.rpy.system.domain.Loginfo;
import com.rpy.system.domain.Menu;
import com.rpy.system.service.LoginfoService;
import com.rpy.system.service.MenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
class ErpApplicationTests {

    @Autowired
    private LoginfoService loginfoService;

    @Test
    void contextLoads() {
        for (int i = 0; i < 100; i++) {
            loginfoService.save(new Loginfo(i,"123","11111",new Date()));
        }
    }

}
