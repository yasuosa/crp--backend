package com.rpy.system.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Auther 任鹏宇
 * @Date 2020/2/27
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class MenuVo extends BaseVo {
    Integer available;

}
