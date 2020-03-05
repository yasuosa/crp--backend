package com.rpy.business.vo;

import com.rpy.system.vo.BaseVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Auther 任鹏宇
 * @Date 2020/3/5
 */

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerVo extends BaseVo {

    private String customername;

    private String phone;
    private String connectionperson;

    private Integer available;

}
