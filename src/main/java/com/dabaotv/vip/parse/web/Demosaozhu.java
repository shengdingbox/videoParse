package com.dabaotv.vip.parse.web;

import org.springframework.web.bind.annotation.RestController;

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
public class Demosaozhu {



    public void a123(String videoUrl) {
        //File file = new File("FoXakn1Zx_JXn6AhPxpkXruT4kvt.m3u8");
        try{
            URL url = new URL(videoUrl);
            //构造一个BufferedReader类来读取文件
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String s = null;
            StringBuilder stringBuffer = new StringBuilder();
            //使用readLine方法，一次读一行
            while((s = br.readLine())!=null){
                stringBuffer.append(s);
            }
            String s1 = stringBuffer.toString();
            String replace = s1.replace(" ", "");
            System.out.println(replace);
            int begin = replace.indexOf("varhuiid=\"");
            int end = replace.indexOf("\";</script><scripttype=\"text/javascript\"src=\"https://cdn.jsdelivr.net/gh/qingee/AlidliPlayer-js/ckplayerx/player.js\">");
            String substring = replace.substring(begin, end).substring("varhuiid=\"".length());
            System.out.println(substring);
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Demosaozhu demosaozhu = new Demosaozhu();
        demosaozhu.a123("https://b.saozhuys.com/qd/m3u8.php?url=https://v.youku.com/v_show/id_XNDkxMTgwMjYzMg==.html");
    }
}
