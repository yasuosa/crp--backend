package com.rpy.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rpy.system.domain.Loginfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Auther 任鹏宇
 * @Date 2020/2/26
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginfoVo extends BaseVo {

    private String loginname;

    private String loginip;

    private Date startTime;

    private Date endTime;

}
