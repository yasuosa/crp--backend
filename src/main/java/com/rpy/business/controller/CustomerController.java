package com.rpy.business.controller;

import com.rpy.system.common.Constant;
import com.rpy.system.common.DataGirdView;
import com.rpy.system.common.ResultObj;
import com.rpy.business.domain.Customer;
import com.rpy.system.domain.User;
import com.rpy.business.service.CustomerService;
import com.rpy.system.utils.SessionDataUtils;
import com.rpy.business.vo.CustomerVo;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;

/**
 * @Auther 任鹏宇
 * @Date 2020/2/26
 */
@RestController
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    /**
     * 查询所有
     * @param customerVo
     * @return
     */
    @RequestMapping(value = "loadAllCustomer",method = RequestMethod.GET)
    public DataGirdView loadAllCustomer(CustomerVo customerVo){
        return customerService.queryAllCustomer(customerVo);
    }

    /**
     * 添加
     * @param customer
     * @return
     */
    @RequestMapping(value = "addCustomer", method = RequestMethod.POST)
    public ResultObj addCustomer(Customer customer){
        try {
            customer.setAvailable(Constant.AVAILABLE_TRUE);
            customerService.saveCustomer(customer);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_FAIL;
        }
    }

    /**
     * 删除
     * @return
     */
    @RequestMapping(value = "delCustomer", method = RequestMethod.POST)
    public ResultObj addCustomer(Integer id){
        try {
            if(null == id){
                return ResultObj.DELETE_WRONG;
            }
            customerService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_FAIL;
        }
    }

    /**
     * 修改
     * @return
     */
    @RequestMapping(value = "updateCustomer", method = RequestMethod.POST)
    public ResultObj updateCustomer(Customer customer){
        try {
            if(null==customer.getId()){
                return ResultObj.UPDATE_WRONG;
            }
            customerService.updateCustomer(customer);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_FAIL;
        }
    }



    /**
     * 批量删除
     * @return
     */
    @RequestMapping(value = "batchDelCustomer", method = RequestMethod.POST)
    public ResultObj addCustomer(Integer[] ids){
        if(null == ids || ids.length<=1){
            return ResultObj.DELETE_WRONG;
        }
        try {
            for (Integer id : ids) {
                customerService.removeById(id);
            }
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_FAIL;
        }
    }

}
