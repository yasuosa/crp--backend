package com.rpy.system.common;

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
public class AuthErrorException {

    private Integer code;
    private String msg;


}
