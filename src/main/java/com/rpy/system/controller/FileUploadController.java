package com.rpy.system.controller;

import com.rpy.system.common.DataGirdView;
import com.rpy.system.common.UploadImgResult;
import com.rpy.system.utils.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther 任鹏宇
 * @Date 2020/2/27
 */

@RestController
@RequestMapping("upload")
public class FileUploadController {

    @Autowired
    private UploadService uploadService;

    @RequestMapping(value = "uploadImgs",method = RequestMethod.POST)
    public UploadImgResult uploadImgs(MultipartFile[] mfs){
        try {
            List<String> urls=new ArrayList<>();
            for (MultipartFile mf : mfs) {
                urls.add(uploadService.uploadImage(mf));
            }
            return new UploadImgResult(0,"上传成功",urls);
        } catch (Exception e) {
            e.printStackTrace();
            return new UploadImgResult(-1,"上传失败|"+e.getMessage(),null);
        }
    }
}
