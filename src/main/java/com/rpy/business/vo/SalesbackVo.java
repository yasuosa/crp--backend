package com.rpy.business.vo;

import com.rpy.system.vo.BaseVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Auther 任鹏宇
 * @Date 2020/3/5
 */

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesbackVo extends BaseVo {

    private Integer providerid;

    private Integer goodsid;
    private Date startTime;

    private Date endTime;

}
