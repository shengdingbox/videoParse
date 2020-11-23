package com.dabaotv.vip.parse.util;

import com.alibaba.fastjson.JSONObject;
import com.dabaotv.vip.parse.dto.VideoUrl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;

/**
 * @author 周子斐
 * parse
 * @date 2020/10/23
 * @Description
 */
@Component
@Slf4j
public class ParseUtils {


    public VideoUrl JXDS(String url) {
        log.info("JXDS解析视频,解析地址为{}",url);
        String jiexiUrl = "https://user.htv009.com/json?url=";
        String data = HttpData.getData(jiexiUrl + url);
        VideoUrl videoUrl = JSONObject.parseObject(data, VideoUrl.class);
        String code = videoUrl.getCode();
        if (!"200".equals(code)) {
            //解析失败，
            log.error("------------------JXDS解析视频失败,正在转换CKMOV-------------------");
            return CKMOV(url);
        }
        log.info("JXDS解析视频,解析完成{}",videoUrl);
        return videoUrl;
    }
    public VideoUrl CKMOV(String url) {
        log.info("CKMOV解析视频,解析地址为{}",url);
        String jiexiUrl = "http://api.dabaotv.cn/nxflv/json.php?url=";
        String data = HttpData.getData(jiexiUrl + url);
        VideoUrl videoUrl = JSONObject.parseObject(data, VideoUrl.class);
        log.info("CKMOV解析视频,解析完成{}",videoUrl);
        return videoUrl;
    }
    public VideoUrl SAOZHU(String url) {
        log.info("SAOZHU解析视频,解析地址为{}",url);
        String jiexiUrl = "http://sz.saozhuys.com/analysis/json/?uid=32&my=bjorwxyEFJSUZ23458&url=";
        String data = HttpData.getData(jiexiUrl + url);
        VideoUrl videoUrl = JSONObject.parseObject(data, VideoUrl.class);
        String code = videoUrl.getCode();
        if (!"200".equals(code)) {
            //解析失败，
            log.error("------------------SAOZHU解析视频失败,正在转换JXDS-------------------");
            return JXDS(url);
        }
        log.info("SAOZHU解析视频,解析完成{}",videoUrl);
        return videoUrl;
    }

    public static void main(String[] args) throws IOException {
        int timeOut = 3000 ;
        //boolean status = InetAddress.getByName("www.baidu.com").isReachable(timeOut);
//        System.out.println(status);

//        Process p = Runtime.getRuntime().exec("ping " + "www.dabaotv.cn");
//        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
//        String line = null;
//        StringBuilder sb = new StringBuilder();
//        while ((line = br.readLine()) != null) {
//            if (line.length() != 0)
//                sb.append(line + "\r\n");
//        }
//        System.out.println("本次指令返回的消息是：");
//        System.out.println(sb.toString());

        InetAddress byName = InetAddress.getByName("sz.saozhuys.com");
        System.out.println(byName);
    }
}
