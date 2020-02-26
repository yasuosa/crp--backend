package com.rpy.system.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @Auther 任鹏宇
 * @Date 2020/2/26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class NoticeVo extends BaseVo {

    private String title;

    private String opername;

    private Date startTime;

    private Date endTime;
}
