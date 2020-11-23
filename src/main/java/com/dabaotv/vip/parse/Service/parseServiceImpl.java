package com.dabaotv.vip.parse.Service;

import com.alibaba.fastjson.JSONObject;
import com.dabaotv.vip.parse.dto.VideoUrl;
import com.dabaotv.vip.parse.repository.VideoRepository;
import com.dabaotv.vip.parse.util.*;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.awt.image.VolatileImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

/**
 * @author 周子斐
 * parse
 * @date 2020/7/23
 * @Description
 */
@Service
@Slf4j
public class parseServiceImpl implements ParseService {

    @Autowired
    RedisUtils redisUtils;
    @Autowired
    ParseUtils parseUtils;

    @Autowired
    VideoRepository videoRepository;

    @Autowired
    private AmqpTemplate template;

    @Value("${file.url}")
    String HttpUrl;


    @Override
    public String parseUrl(String url) {
        if (url.contains("." + VideoType.M3U8.getType())
                || url.contains("." + VideoType.MP4.getType())) {
            VideoUrl videoUrl = new VideoUrl();
            videoUrl.setUrl(url);
            videoUrl.setCode("200");
            videoUrl.setPlayer("ckplayer");
            videoUrl.setType("m3u8");
            return JSONObject.toJSONString(videoUrl);
        }
        Object o = redisUtils.get(url);
        if (o != null) {
            String o1 = (String) o;
            log.info("视频解析成功,原始地址为{},解析地址为{}", url, o1);
            return o1;
        }
//        VideoUrl videoByUrl = videoRepository.findVideoByUrl(url);
//        if (videoByUrl != null) {
//            videoByUrl.setUrl(HttpUrl + videoByUrl.getUrl());
//            String s = JSONObject.toJSONString(videoByUrl);
//            redisUtils.set(videoByUrl.getOriginalUrl(), s);
//            return s;
//        }
        //String jxApi = VideoType.getValue(url);
        String key = VideoType.getKey(url);
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                VideoUrl videoUrl = parseUtils.JXDS(url);
                parseVideo(videoUrl,url,key);
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                VideoUrl videoUrl = parseUtils.CKMOV(url);
                parseVideo(videoUrl,url,key);
            }
        });
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                VideoUrl videoUrl = parseUtils.SAOZHU(url);
                parseVideo(videoUrl,url,key);
            }
        });
        thread1.start();
        thread2.start();
        thread3.start();
//        switch (jxApi) {
//            case "JXDS":
//                videoUrl = parseUtils.JXDS(url);
//                break;
//            case "CKMOV":
//                videoUrl = parseUtils.CKMOV(url);
//                break;
//            default:
//
//        }
        boolean isTrue = true;
        while (isTrue) {
            boolean b = redisUtils.hasKey(url);
            isTrue = !b;

        }
        //videoUrl.setUrl(url + videoUrl.getUrl());
        return (String) redisUtils.get(url);
    }

    public void parseVideo(VideoUrl videoUrl,String url,String key){
        videoUrl.setOriginalUrl(url);
        videoUrl.setPrefixType(key);
        //开始保存MQ
        if("200".equals(videoUrl.getCode())){
            template.convertAndSend(Queues.SAVE, videoUrl);
            String s = JSONObject.toJSONString(videoUrl);
            redisUtils.set(videoUrl.getOriginalUrl(), s, 86635);
            log.info("当前线程为{},响应为{}", Thread.currentThread().getName(), s);
        }else{
            log.info("当前线程为{}", Thread.currentThread().getName());
        }
    }
}
