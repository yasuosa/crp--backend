package com.rpy.system.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

import java.util.UUID;

/**
 * @Auther 任鹏宇
 * @Date 2020/3/2
 */
public class MD5Utils {


    /**
     * 生成uuid
     *
     * @return
     */
    public static String createUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    /**
     * 对面进行加密
     * @param source
     * @param slat
     * @param has
     * @return
     */
    public static String md5(String source,String slat,Integer has){
        Md5Hash hash=new Md5Hash(source,slat,has);
        return hash.toString();
    }
}
