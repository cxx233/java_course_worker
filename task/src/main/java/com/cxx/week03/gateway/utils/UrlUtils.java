package com.cxx.week03.gateway.utils;

public class UrlUtils {

    public static String formatUrl(String backend) {
        //　切除最后以/ 结尾的地址
        return backend.endsWith("/") ? backend.substring(0, backend.length() - 1) : backend;
    }
}
