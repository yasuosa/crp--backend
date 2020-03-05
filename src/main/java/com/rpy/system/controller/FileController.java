package com.rpy.system.controller;

import com.rpy.system.common.DataGirdView;
import com.rpy.system.common.upload.UploadProperties;
import com.rpy.system.domain.User;
import com.rpy.system.service.UserService;
import com.rpy.system.utils.SessionDataUtils;
import com.rpy.system.utils.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther 任鹏宇
 * @Date 2020/3/5
 * 文件上传
 */
@RestController
@RequestMapping("file")
public class FileController {

    @Autowired
    private UserService userService;

    @Autowired
    private UploadProperties properties;

    @Autowired
    private UploadService uploadService;

    /**
     * 更新头像
     * @param mf
     * @return
     */
    @RequestMapping(value = "uploadFile",method = RequestMethod.POST)
    public Object uploadFile(MultipartFile mf){
        String path = uploadService.uploadImage(mf);
        Map<String,Object> map=new HashMap<>();
        map.put("src",path);
        User user = SessionDataUtils.getUser();
        user.setImgpath(path);
        userService.updateUser(user);
        return new DataGirdView(map);
    }

    /**
     * 上传供应商的商品图片
     * @param mf
     * @return
     */
    @RequestMapping(value = "uploadGoodsFile",method = RequestMethod.POST)
    public Object uploadGoodsFile(MultipartFile mf){
        String path = uploadService.uploadImage(mf);
        Map<String,Object> map=new HashMap<>();
        map.put("src",path);
        return new DataGirdView(map);
    }
}
