package com.rpy.business.service;

import com.rpy.business.domain.Outport;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rpy.business.vo.OutportVo;
import com.rpy.system.common.DataGirdView;

public interface OutportService extends IService<Outport>{


    DataGirdView queryAllOutport(OutportVo outportVo);

    Outport saveOutport(Outport outport);

    Outport updateOutport(Outport outport);
}
