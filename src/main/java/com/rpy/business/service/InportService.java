package com.rpy.business.service;

import com.rpy.business.domain.Inport;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rpy.business.vo.InportVo;
import com.rpy.system.common.DataGirdView;

public interface InportService extends IService<Inport>{


    Inport saveInport(Inport inport);

    Inport updateInport(Inport inport);

    DataGirdView queryAllInport(InportVo inportVo);

}
