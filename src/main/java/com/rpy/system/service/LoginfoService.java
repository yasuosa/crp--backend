package com.rpy.system.service;

import com.rpy.system.common.DataGirdView;
import com.rpy.system.domain.Loginfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rpy.system.vo.LoginfoVo;

public interface LoginfoService extends IService<Loginfo>{


    DataGirdView queryAllLoginfo(LoginfoVo loginfoVo);
}
