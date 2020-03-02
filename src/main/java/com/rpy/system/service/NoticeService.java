package com.rpy.system.service;

import com.rpy.system.common.DataGirdView;
import com.rpy.system.domain.Notice;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rpy.system.vo.NoticeVo;

public interface NoticeService extends IService<Notice>{


    DataGirdView queryAllNotice(NoticeVo noticeVo);
}
