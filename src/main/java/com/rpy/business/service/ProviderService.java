package com.rpy.business.service;

import com.rpy.business.domain.Provider;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rpy.business.vo.ProviderVo;
import com.rpy.system.common.DataGirdView;

public interface ProviderService extends IService<Provider>{


    DataGirdView queryAllProvider(ProviderVo providerVo);

    Provider saveProvider(Provider provider);

    Provider updateProvider(Provider provider);

}
