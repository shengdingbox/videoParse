package com.dabaotv.vip.parse.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 周子斐
 * yingshivip_admin
 * @date 2020/10/12
 * @Description
 */
@Slf4j
@Component
public class CollectUtils {

    @Autowired
    RedisUtils redisUtils;

    @Value("classpath:collect.json")
    Resource collect;

    public String collectVideo(String args) throws Exception {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        Object o = redisUtils.get(args);
        int total = 0;
        if (o != null) {
            String o1 = (String) o;
            total = Integer.parseInt(o1) - 1;
        }
        String cookie = login();
        log.info("登陆成功cookie为{}",cookie);
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss");
        String dateTime = dateFormat.format(date);
        String areaData = IOUtils.toString(collect.getInputStream(), StandardCharsets.UTF_8);
        JSONObject jsonObject = JSONObject.parseObject(areaData);
        String flag = (String) jsonObject.get(args);
        if (StringUtils.isBlank(flag)) {
            flag = "122b22a6de622fa999bfe200854d5213";
        }
        log.info("flag为" + flag);
        while (true) {
            try {
                total++;
                redisUtils.set(args, String.valueOf(total));
                result = new StringBuilder();
                log.info("开始执行任务--->" + args + "当前时间为" + dateTime + "当前执行页数为:" + total);
                String urlNameString = "https://www.dabaotv.cn/admin.php/admin/collect/api.html?ac=cj&cjflag=" + flag + "&cjurl=";
                String param = "&h=24&t=&ids=&wd=&type=1&mid=1&opt=0&filter=0&filter_from=&param=&page=";
                URL realUrl = new URL(urlNameString + args + param + total);
                // 打开和URL之间的连接
                URLConnection connection = realUrl.openConnection();
                // 设置通用的请求属性
                connection.setRequestProperty("accept", "*/*");
                connection.setRequestProperty("connection", "Keep-Alive");
                connection.setRequestProperty("cookie", cookie);
                // 建立实际的连接
                connection.connect();
                // 获取所有响应头字段
                Map<String, List<String>> map = connection.getHeaderFields();
                // 遍历所有的响应头字段
                for (String key : map.keySet()) {
                    log.error(key + "--->" + map.get(key));
                }
                // 定义 BufferedReader输入流来读取URL的响应
                in = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    result.append(line);
                }
                log.info("延时1秒");
                Thread.sleep(1000);
            } catch (Exception e) {
                log.error("发送GET请求出现异常！" + e);
                e.printStackTrace();
            }
            // 使用finally块来关闭输入流
            finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            log.error(String.valueOf(result));
            //截取?之后字符串
            String num = result.substring(result.indexOf("<strong class=\"green\">"), result.indexOf("</strong>")).substring("<strong class=\"green\">".length());
            String sum = result.substring(result.indexOf("<span class=\"green\">"), result.indexOf("</span")).substring("<span class=\"green\">".length());
            log.info("执行任务执行完成===>" + args + "当前时间为" + dateTime + "当前执行页数为:" + num + "总执行页数为:" + sum);
            if (num.equals(sum)) {
                redisUtils.del(args);
                return dateTime + "执行任务完成" + args + "共执行页数为" + sum;

            }
            if (Integer.parseInt(num) > Integer.parseInt(sum)) {
                redisUtils.del(args);
                return dateTime + "执行任务完成" + args + "共执行页数为" + sum;

            }
        }
    }

    public String login() {
        Map<String, String> map = new HashMap<>();
        map.put("admin_name", "feifei");
        map.put("admin_pwd", "feifei");
        Map<String, String> hear = new HashMap<>();
        StringBuilder stringBuffer = new StringBuilder();
        try {
            HttpResponse post = HttpUtils.doPost("https://www.dabaotv.cn"
                    , "/admin.php/admin/index/login.html"
                    , "post"
                    , hear
                    , null
                    , map);
            Header[] allHeaders = post.getAllHeaders();
            for (Header allHeader : allHeaders) {
                if ("Set-Cookie".equals(allHeader.getName())) {
                    stringBuffer.append(allHeader.getValue());
                    stringBuffer.append(";");
                }
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        CollectUtils collectUtils = new CollectUtils();
        String login = collectUtils.login();
        System.out.println(login);
    }
}
