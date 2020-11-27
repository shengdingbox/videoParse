package com.dabaotv.vip.web;

import com.dabaotv.vip.service.ParseService;
import com.dabaotv.vip.util.IpUtil;
import com.dabaotv.vip.util.VideoType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author 周子斐
 * parse
 * @date 2020/7/23
 * @Description
 */
@RestController
@Slf4j
@RequestMapping("parse")
public class parseAPI {

    @Autowired
    private ParseService parseService;

    @Autowired
    HttpServletRequest request;

    @GetMapping()
    public String parseUrl(String url){
        log.info(IpUtil.getIpAddr(request));
        if(StringUtils.isEmpty(url)){
            return "请输入url";
        }
        String ipAddr = IpUtil.getIpAddr(request);
        log.info("来源地址为{}",ipAddr);
        if(url.contains(VideoType.MIGU.getType())){
            return parseService.parseUrl(url);
        }
        if(url.contains(".html")){
            int html = url.lastIndexOf("html");
            url = url.substring(0, html) + "html";
        }
        if(url.contains(".shtml")){
            int html = url.lastIndexOf("shtml");
            url = url.substring(0, html) + "shtml";
        }
        if(url.contains(VideoType.PPX.getType())){
            try {
                return PPXUtil.getData(url);
            } catch (IOException e) {
                return "error";
            }
        }
        return parseService.parseUrl(url);
    }
}
