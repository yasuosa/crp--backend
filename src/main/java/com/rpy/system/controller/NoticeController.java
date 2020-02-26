package com.rpy.system.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.rpy.system.common.ActiveUser;
import com.rpy.system.common.DataGirdView;
import com.rpy.system.common.ResultObj;
import com.rpy.system.domain.Notice;
import com.rpy.system.domain.User;
import com.rpy.system.service.NoticeService;
import com.rpy.system.utils.SessionDataUtils;
import com.rpy.system.vo.NoticeVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;

/**
 * @Auther 任鹏宇
 * @Date 2020/2/26
 */
@RestController
@RequestMapping("notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;


    /**
     * 查询所有
     * @param noticeVo
     * @return
     */
    @RequestMapping(value = "loadAllNotice",method = RequestMethod.GET)
    public DataGirdView loadAllNotice(NoticeVo noticeVo){
        return noticeService.queryAllNotice(noticeVo);
    }

    /**
     * 添加
     * @param notice
     * @return
     */
    @RequestMapping(value = "addNotice", method = RequestMethod.POST)
    public ResultObj addNotice(Notice notice){
        try {
            User user = SessionDataUtils.getUser();
            notice.setOpername(user.getName());
            notice.setCreatetime(new Date());
            noticeService.save(notice);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_FAIL;
        }
    }

    /**
     * 删除
     * @return
     */
    @RequestMapping(value = "delNotice", method = RequestMethod.POST)
    public ResultObj addNotice(Integer id){
        try {
            if(null == id){
                return ResultObj.DELETE_WRONG;
            }
            noticeService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_FAIL;
        }
    }

    /**
     * 修改
     * @return
     */
    @RequestMapping(value = "updateNotice", method = RequestMethod.POST)
    public ResultObj updateNotice(Notice notice){
        try {
            if(null==notice.getId()){
                return ResultObj.UPDATE_WRONG;
            }
            noticeService.updateById(notice);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_FAIL;
        }
    }



    /**
     * 批量删除
     * @return
     */
    @RequestMapping(value = "batchDelNotice", method = RequestMethod.POST)
    public ResultObj addNotice(Integer[] ids){
        if(null == ids || ids.length<=1){
            return ResultObj.DELETE_WRONG;
        }
        try {
            noticeService.removeByIds(Arrays.asList(ids));
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_FAIL;
        }
    }

}
