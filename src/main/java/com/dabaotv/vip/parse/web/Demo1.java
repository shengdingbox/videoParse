package com.dabaotv.vip.parse.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * @author 周子斐
 * parse
 * @date 2020/7/23
 * @Description
 */
public class Demo1 {
    public static void main(String[] args) {
        //File file = new File("FoXakn1Zx_JXn6AhPxpkXruT4kvt.m3u8");
        try{
            URL url = new URL("https://images.dabaotv.cn/FoXakn1Zx_JXn6AhPxpkXruT4kvt");
            StringBuilder result = new StringBuilder();
            //构造一个BufferedReader类来读取文件
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String s = null;
            //使用readLine方法，一次读一行
            while((s = br.readLine())!=null){
                if(!s.contains("#")){
                    result.append( "https://10235.vcdn.pplive.cn" + s + "\n");
                }else {
                    result.append( s + "\n");
                }
            }
            System.out.println(result);
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
