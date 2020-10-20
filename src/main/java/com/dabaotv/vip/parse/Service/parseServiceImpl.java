package com.dabaotv.vip.parse.Service;

import com.alibaba.fastjson.JSONObject;
import com.dabaotv.vip.parse.dto.VideoUrl;
import com.dabaotv.vip.parse.repository.VideoRepository;
import com.dabaotv.vip.parse.util.HttpData;
import com.dabaotv.vip.parse.util.Queues;
import com.dabaotv.vip.parse.util.RedisUtils;
import com.dabaotv.vip.parse.util.VideoType;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class parseServiceImpl implements ParseService {

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    VideoRepository videoRepository;

    @Autowired
    private AmqpTemplate template;

    @Override
    public String parseUrl(String url) {
        Object o = redisUtils.get(url);
        if (o != null) {
            return (String) o;
        }
        VideoUrl videoByUrl = videoRepository.findVideoByUrl(url);
        if(videoByUrl != null){
            videoByUrl.setUrl("https://m3u8.dabaotv.cn/" + videoByUrl.getUrl());
            String s = JSONObject.toJSONString(videoByUrl);
            redisUtils.set(videoByUrl.getOriginalUrl(), s);
            return s;
        }
        if (url.contains("." + VideoType.M3U8.getType())
                || url.contains("." + VideoType.MP4.getType())) {
            VideoUrl videoUrl = new VideoUrl();
            videoUrl.setUrl(url);
            videoUrl.setCode("200");
            videoUrl.setPlayer("ckplayer");
            videoUrl.setType("m3u8");
            return JSONObject.toJSONString(videoUrl);
//        } else if (url.contains(VideoType.MGTV.getType())
//                || url.contains(VideoType.QQ.getType())
//                || url.contains(VideoType.PPTV.getType())
//                || url.contains(VideoType.AQIYI.getType())
//                || url.contains(VideoType.MIGU.getType())) {
//            VideoUrl videoUrl = JXDS(url);
//            return JSONObject.toJSONString(videoUrl);
//        } else {
        }else{
//            VideoUrl videoUrl = CKMOV(url);
            VideoUrl videoUrl = SAOZHU(url);
            return JSONObject.toJSONString(videoUrl);
        }
    }

    public VideoUrl JXDS(String url) {
        String jiexiUrl = "https://user.htv009.com/json?url=";
        String data = HttpData.getData(jiexiUrl + url);
        VideoUrl videoUrl = JSONObject.parseObject(data, VideoUrl.class);
        String code = videoUrl.getCode();
        String type = videoUrl.getType();
        if (!"200".equals(code)
                || url.contains(VideoType.MGTV.getType())
                || url.contains(VideoType.QQ.getType())
                || url.contains(VideoType.PPTV.getType())) {
            //解析失败，待完善,土豆500,乐视404,1904-404,360-500,音悦台500,华数404,新浪500,开眼视频500,2mm-500
            return videoUrl;
        }
        if (VideoType.M3U8.getType().equals(type) || VideoType.HLS.getType().equals(type)) {
            videoUrl.setOriginalUrl(url);
            template.convertAndSend(Queues.SAVE,videoUrl);
        }
        return videoUrl;
    }
    public VideoUrl SAOZHU(String url) {
        String jiexiUrl = "http://sz.saozhuys.com/analysis/json/?uid=32&my=bjorwxyEFJSUZ23458&url=";
        String data = HttpData.getData(jiexiUrl + url);
        VideoUrl videoUrl = JSONObject.parseObject(data, VideoUrl.class);
        String code = videoUrl.getCode();
        String type = videoUrl.getType();
//        if (!"200".equals(code)
//                || url.contains(VideoType.MGTV.getType())
//                || url.contains(VideoType.QQ.getType())
//                || url.contains(VideoType.PPTV.getType())) {
//            //解析失败，待完善,土豆500,乐视404,1904-404,360-500,音悦台500,华数404,新浪500,开眼视频500,2mm-500
//            return videoUrl;
//        }
//        if (VideoType.M3U8.getType().equals(type) || VideoType.HLS.getType().equals(type)) {
//            videoUrl.setOriginalUrl(url);
//            template.convertAndSend(Queues.SAVE,videoUrl);
//        }
        return videoUrl;
    }

    public VideoUrl CKMOV(String url) {
        String jiexiUrl = "http://api.dabaotv.cn/nxflv/json.php?url=";
        String data = HttpData.getData(jiexiUrl + url);
        VideoUrl videoUrl = JSONObject.parseObject(data, VideoUrl.class);
        String code = videoUrl.getCode();
        String type = videoUrl.getType();
        if (!"200".equals(code)) {
            //解析失败，待完善,乐视404,音悦台500,华数404,新浪没地址,开眼视频404,2mm-无法播放,梨视频404
            return videoUrl;
        }
        if (VideoType.M3U8.getType().equals(type) || VideoType.HLS.getType().equals(type)) {
            videoUrl.setOriginalUrl(url);
            template.convertAndSend(Queues.SAVE,videoUrl);
        }
        return videoUrl;
    }


}
