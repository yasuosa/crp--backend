package com.rpy.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rpy.system.common.DataGirdView;
import com.rpy.system.common.ResultObj;
import com.rpy.system.domain.Loginfo;
import com.rpy.system.service.LoginfoService;
import com.rpy.system.vo.LoginfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @Auther 任鹏宇
 * @Date 2020/2/26
 */

@RestController
@RequestMapping("loginfo")
public class LogInfoController {

    @Autowired
    private LoginfoService loginfoService;

    /**
     * 查询总日志记录
     * @param loginfoVo
     * @return
     */
    @RequestMapping(value = "loadAllLoginfo",method = RequestMethod.GET)
    public DataGirdView loadAllLoginfo(LoginfoVo loginfoVo){
        System.out.println(loginfoVo.toString());
        return  loginfoService.queryAllLoginfo(loginfoVo);
    }

    /**
     * 删除日志
     * @param id
     * @return
     */
    @RequestMapping(value = "delLoginfo",method = RequestMethod.POST)
    public ResultObj delLogingo(Integer id){
        if(null == id){
            return ResultObj.DELETE_WRONG;
        }
        try {
            loginfoService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_FAIL;
        }
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "batchDelLoginfo",method = RequestMethod.POST)
    public ResultObj batchDelLogingo(Integer[] ids){
        if(null!=ids && ids.length>1){
            try {
                loginfoService.removeByIds(Arrays.asList(ids));
                return ResultObj.DELETE_SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
                return ResultObj.DELETE_FAIL;
            }
        }else {
            return ResultObj.DELETE_WRONG;
        }

    }

}
