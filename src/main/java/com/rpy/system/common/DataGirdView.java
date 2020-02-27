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
public class DataGirdView {

    private Integer code=0;

    private String msg="";

    private Long count=0L;

    private Object data;

    public DataGirdView(Long count, Object data) {
        this.count = count;
        this.data = data;
    }

    public DataGirdView(Object data) {
        this.data = data;
    }
}
