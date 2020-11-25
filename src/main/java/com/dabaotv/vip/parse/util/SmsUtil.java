package com.dabaotv.vip.parse.util;


import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *
 */
@Component
public class SmsUtil {

    public static ExpiringMap<String,String> map = ExpiringMap.builder()
            .maxSize(100)
            .expiration(60, TimeUnit.SECONDS)
            .expirationPolicy(ExpirationPolicy.ACCESSED)
            .variableExpiration()
            .build();

    @Value("${sms.appcode}")
    String appcode;
    @Value("${sms.accessKeyId}")
    String accessKeyId;
    @Value("${sms.secret}")
    String secret;
    @Value("${sms.SignName}")
    String signName;
    @Value("${sms.TemplateCode}")
    String templateCode;

    @Autowired
    RedisUtils redisUtils;

    public  String sendSmsShop(String mobile,String rand){
        String host = "https://zwp.market.alicloudapi.com";
        String path = "/sms/sendv2";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + "5b1999f97aad43d98f7a08069ae75afc");
        Map<String, String> querys = new HashMap<String, String>();
        int passCode = Integer.parseInt(rand);
        String passCodes = map.get(mobile);
        if(StringUtils.isNotBlank(passCodes)){
            return "短信已发送,请等待";
        }
        map.put(mobile,String.valueOf(passCode));
        //querys.put("content", "【影视VIP】您的验证码是"+passCode+"。请不要把验证码泄露给其他人！5分钟内有效。我们的官方网址为http://suo.im/5X58kT");
        querys.put("content", "【云通知】您的验证码是"+passCode+"。如非本人操作，请忽略本短信");
        querys.put("mobile", mobile);


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             * 【云通知】您的验证码是"+passCode+"。如非本人操作，请忽略本短信
             * 【影视VIP】您的验证码是#code#。请不要把验证码泄露给其他人！15分钟内有效。我们的官方网址为http://suo.im/5X58kT
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "短信发送成功,短信有效期为5分钟";
    }
    public String sendSms(String mobile,String rand){
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, secret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.GET);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", mobile);
        byte[] bytes = Base64.getDecoder().decode(signName);
        String sign = new String(bytes);
        request.putQueryParameter("SignName", sign);
        request.putQueryParameter("TemplateCode", templateCode);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",rand);
        request.putQueryParameter("TemplateParam", jsonObject.toJSONString());
        String smsKey = "SMS_SEND_";
        String passCodes = map.get(smsKey + mobile);
        if(StringUtils.isNotBlank(passCodes)){
            return "短信已发送,请等待";
        }
        redisUtils.set(smsKey + mobile,String.valueOf(rand),300);
        map.put(smsKey + mobile,String.valueOf(rand));
        try {
           CommonResponse response = client.getCommonResponse(request);
           System.out.println(response.getData());
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return "短信发送成功,短信有效期为5分钟";
    }

    public static void main(String[] args) {
        byte[] encode = Base64.getEncoder().encode("蜜蜂追剧".getBytes());
        System.out.println(new String(encode));
        byte[] decode = Base64.getDecoder().decode(encode);
        System.out.println(new String(decode));
    }
}
