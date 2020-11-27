package com.dabaotv.vip.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * @author 周子斐
 * parse
 * @date 2020/7/23
 * @Description
 */
@RestController
public class Demo1 {
    @Autowired
    HttpServletResponse response;

    @GetMapping("test")
    public void a123(String videoUrl) {
        //File file = new File("FoXakn1Zx_JXn6AhPxpkXruT4kvt.m3u8");
        try{
            URL url = new URL(videoUrl);
            StringBuilder result = new StringBuilder();
            //构造一个BufferedReader类来读取文件
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String s = null;
            //使用readLine方法，一次读一行
            while((s = br.readLine())!=null){
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(Integer.parseInt(s));
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
