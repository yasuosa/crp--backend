package com.rpy.business.service;

import com.rpy.business.domain.Customer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rpy.business.vo.CustomerVo;
import com.rpy.system.common.DataGirdView;

public interface CustomerService extends IService<Customer>{


    DataGirdView queryAllCustomer(CustomerVo customerVo);

    Customer saveCustomer(Customer customer);

    Customer updateCustomer(Customer customer);

    DataGirdView getAllAvailableCustomer();

}
