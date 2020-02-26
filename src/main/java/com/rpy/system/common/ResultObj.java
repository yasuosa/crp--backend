package com.rpy.system.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther 任鹏宇
 * @Date 2020/2/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultObj {
    public static final ResultObj IS_LOGIN =new ResultObj(200,"已登录") ;
    public static final ResultObj UN_LOGIN =new ResultObj(-1,"未登录") ;

    public static final ResultObj DELETE_SUCCESS = new ResultObj(200,"删除成功");
    public static final ResultObj DELETE_FAIL = new ResultObj(-1,"删除失败");
    public static final ResultObj DELETE_WRONG = new ResultObj(-1,"删除失败|请至少选中2个");;
    public static final ResultObj ADD_SUCCESS = new ResultObj(200,"添加成功") ;
    public static final ResultObj ADD_FAIL = new ResultObj(-1,"添加失败") ;
    public static final ResultObj UPDATE_SUCCESS = new ResultObj(200,"修改成功") ;
    public static final ResultObj UPDATE_FAIL = new ResultObj(-1,"修改失败") ;
    public static final ResultObj UPDATE_WRONG = new ResultObj(-1,"修改失败|未指定目标");
    private Integer code=200;
    private String msg=null;
    private String token=null;

    public ResultObj(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
