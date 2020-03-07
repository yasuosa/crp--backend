package com.rpy.business.service;

import com.rpy.business.domain.Sales;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rpy.business.vo.SalesVo;
import com.rpy.system.common.DataGirdView;

public interface SalesService extends IService<Sales>{


    DataGirdView queryAllSales(SalesVo salesVo);

    Sales saveSales(Sales sales);

    Sales updateSales(Sales sales);

}
