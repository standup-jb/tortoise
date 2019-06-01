package com.standup.tortoise.utils;

/**
 * Created by Jiang Biao 2019/5/29
 */
public class CreateKeyUtils {

    public static String createKey(String method,String url){
        return new StringBuffer(method).append("_").append(url).toString();
    }



}
