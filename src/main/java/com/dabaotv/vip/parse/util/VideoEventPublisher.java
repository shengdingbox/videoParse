package com.dabaotv.vip.parse.util;

import com.alibaba.fastjson.JSONObject;
import com.dabaotv.vip.parse.dto.VideoUrl;
import com.dabaotv.vip.parse.repository.VideoRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author 周子斐
 * parse
 * @date 2020/7/24
 * @Description
 */
@Component
public class VideoEventPublisher {


    @Autowired
    RedisUtils redisUtils;

    @Autowired
    VideoRepository videoRepository;

    @Value("${file.url}")
    String url;
    @Value("${file.local}")
    String local;


    /**
     * 新增视频
     */
    @RabbitListener(queues = Queues.SAVE)
    public void saveCoupFlowEvent(VideoUrl videoUrl) {
        String originalUrl = videoUrl.getOriginalUrl();
        saveM3u8(videoUrl, originalUrl);
        if (StringUtils.isBlank(videoUrl.getUrl())) {
            return;
        }
        VideoUrl videoByUrl = videoRepository.findVideoByUrl(originalUrl);
        if(videoByUrl != null){
            BeanUtils.copyProperties(videoUrl,videoByUrl);
            videoRepository.save(videoByUrl);
        }else{
            videoRepository.save(videoUrl);
        }
    }

    private void saveM3u8(VideoUrl videoUrl, String originalUrl) {
        String domain = null;
        if (videoUrl.getDomain() != null) {
            domain = videoUrl.getDomain();
        }
        String parseUrl = videoUrl.getUrl();
        if (StringUtils.isBlank(parseUrl)) {
            return;
        }
        if (originalUrl.contains(VideoType.CCTV.getType())) {
            //cctv,可以解析  去掉最后一个/后的拼播放地址就行
            int i = parseUrl.lastIndexOf("/");
            String substring = parseUrl.substring(0, i);
            videoUrl.setPrefixUrl(substring + "/");
        }
        //搜狐/优酷/爱奇艺/咪咕
        String save = VideoType.getSave(videoUrl.getOriginalUrl());
        if("Y".equals(save)){
            String playUrl = saveLocal(parseUrl, domain, videoUrl.getPrefixUrl(),videoUrl.getPrefixType());
            videoUrl.setUrl(playUrl);
        }

    }

    public String saveLocal(String url, String domain, String prefixUrl,String prefixType) {
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String replace = UUID.randomUUID().toString().replace("-", "");
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String format = dateFormat.format(new Date());
        if(prefixType.contains(".com")){
            prefixType = prefixType.replace(".com","");
        }
        String filePath = prefixType + "/"+format+"/";
        String fileName = replace + ".m3u8";
        try {
            URL file = new URL(url);
            URLConnection urlConnection = file.openConnection();
            HttpURLConnection connection = (HttpURLConnection) urlConnection;
            // 设定请求的方法，默认是GET
            connection.setRequestMethod("GET");
            // 设置字符编码
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible;MSIE 7.0; Windows NT 5.1; Maxthon;)");
            connection.setRequestProperty("Accept-Encoding", "UTF-8");
            connection.setRequestProperty("referer", domain);
            System.out.println(connection.getContentEncoding());
            // 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            //构造一个BufferedReader类来读取文件
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            //使用readLine方法，一次读一行
            prefixUrl = prefixUrl == null ? "" : prefixUrl;
            File file2 = new File(local+filePath);
            File file1 = new File(local+filePath+fileName);
            if(!file2.isDirectory()){
                file2.mkdirs();
            }
            FileWriter fileWriter = new FileWriter(file1);
            PrintWriter pw = new PrintWriter(new BufferedWriter(fileWriter));
            String ss;
            while ((ss = br.readLine()) != null) {
                pw.println(ss);
            }
            pw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return filePath+fileName;
    }
}
