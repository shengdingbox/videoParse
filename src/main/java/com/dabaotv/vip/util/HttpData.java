package com.dabaotv.vip.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class HttpData {

    public static String getData(String urlPath) {
        StringBuilder sb = new StringBuilder();
        try {
            // 统一资源
            URL url = new URL(urlPath);
            // 连接类的父类，抽象类
            URLConnection urlConnection = url.openConnection();
            // http的连接类
            HttpURLConnection connection = (HttpURLConnection) urlConnection;
            // 设定请求的方法，默认是GET
            connection.setRequestMethod("GET");
            // 设置字符编码
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
//            connection.setInstanceFollowRedirects(false);
            // 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
            connection.connect();

            BufferedInputStream bin = new BufferedInputStream(connection.getInputStream());
            byte[] buf = new byte[1024*2];
            while (bin.read(buf) != -1) {
                String temp = new String(buf);
                sb.append(temp);
            }
            bin.close();
            connection.disconnect();
        } catch (IOException e) {
            return null;
        }
        return sb.toString();
    }
}
