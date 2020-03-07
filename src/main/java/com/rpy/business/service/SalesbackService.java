package com.rpy.business.service;

import com.rpy.business.domain.Salesback;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rpy.business.vo.SalesbackVo;
import com.rpy.system.common.DataGirdView;

public interface SalesbackService extends IService<Salesback>{


    DataGirdView queryAllSalesback(SalesbackVo salesbackVo);

    Salesback saveSalesback(Salesback salesback);

    Salesback updateSalesback(Salesback salesback);
}
