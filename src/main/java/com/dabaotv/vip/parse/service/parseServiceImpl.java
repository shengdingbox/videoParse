package com.dabaotv.vip.parse.service;

import com.alibaba.fastjson.JSONObject;
import com.dabaotv.vip.parse.dto.VideoUrl;
import com.dabaotv.vip.parse.repository.VideoRepository;
import com.dabaotv.vip.parse.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

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
            redisUtils.del(url);
//            String o1 = (String) o;
//            log.info("视频解析成功,原始地址为{},解析地址为{}", url, o1);
//            return o1;
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

        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3);
        Runnable r1 = () -> {
            VideoUrl videoUrl = parseUtils.JXDS(url);
            parseVideo(videoUrl,url,key);
        };
        Runnable r2 = () -> {
            VideoUrl videoUrl = parseUtils.CKMOV(url);
            parseVideo(videoUrl,url,key);
        };
        Runnable r3 = () -> {
            VideoUrl videoUrl = parseUtils.SAOZHU(url);
            parseVideo(videoUrl,url,key);
        };
        scheduledThreadPool.execute(r1);
        scheduledThreadPool.execute(r2);
        scheduledThreadPool.execute(r3);
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
            if(redisUtils.hasKey(url)){
                return;
            }
            redisUtils.set(videoUrl.getOriginalUrl(), s, 86635);
            log.info("当前线程为{},响应为{}", Thread.currentThread().getName(), s);
        }else{
            log.info("当前线程为{},解析失败", Thread.currentThread().getName());
        }
    }
}
