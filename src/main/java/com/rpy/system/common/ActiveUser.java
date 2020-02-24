package com.rpy.system.common;

import com.rpy.system.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther 任鹏宇
 * @Date 2020/2/24
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiveUser implements Serializable {
    private User user;

    private List<String> roles;

    private List<String> permissions;
}
