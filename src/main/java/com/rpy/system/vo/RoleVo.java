package com.rpy.system.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Auther 任鹏宇
 * @Date 2020/3/2
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVo extends BaseVo{
    Integer available;

    String name;
    String remark;
}
