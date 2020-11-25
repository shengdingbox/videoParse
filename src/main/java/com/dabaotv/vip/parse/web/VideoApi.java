package com.dabaotv.vip.parse.web;


import com.dabaotv.vip.parse.util.CollectUtils;
import com.dabaotv.vip.parse.util.RedisUtils;
import com.dabaotv.vip.parse.util.SmsUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author 周子斐
 * yingshivip_admin
 * @date 2020/10/12
 * @Description
 */
@RestController
@RequestMapping("/video")
@Slf4j
public class VideoApi {

    public static ExpiringMap<String,String> map = ExpiringMap.builder()
            .maxSize(100)
            .expiration(60, TimeUnit.MINUTES)
            .expirationPolicy(ExpirationPolicy.ACCESSED)
            .variableExpiration()
            .build();

    @Autowired
    private SmsUtil smsUtil;

    @Autowired
    private CollectUtils collectUtils;

    @Autowired
    RedisUtils redisUtils;

    @ApiOperation(value = "下载zip", notes = "下载zip")
    @GetMapping
    public  String coll(String args) {
        int numInt = 0;
        try {
            if(StringUtils.isBlank(map.get(args))){
                map.put(args,String.valueOf(numInt));
            }
            return collectUtils.collectVideo(args);
        } catch (Exception e) {
            String num = map.get(args);
            if(StringUtils.isNotBlank(num)&&Integer.parseInt(num) < 4){
                String s = String.valueOf(Integer.parseInt(num) + 1);
                map.put(args,s);
                log.error("第"+s+"次重试");
                coll(args);
            }
            if(Integer.parseInt(num) == 3){
                String errCode = String.valueOf(new Random().nextInt(999999));
                String o = (String)redisUtils.get(args);
                redisUtils.set(errCode,"任务名"+args+"执行页数为"+o+"异常为"+e.toString()+"重试次数为"+num);
                String s = smsUtil.sendSmsShop("17611555590", errCode);
                log.error(s);
                redisUtils.del(args);
            }
            e.printStackTrace();
        }
        return "失败";
    }

    public static void main(String[] args) {
        SmsUtil smsUtil = new SmsUtil();
        String s = smsUtil.sendSmsShop("17611555590", "123456");
        log.error(s);
    }
}
