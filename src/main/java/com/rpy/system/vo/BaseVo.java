package com.rpy.system.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther 任鹏宇
 * @Date 2020/2/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseVo {

    private Integer page=1;

    private Integer limit=10;
}
