package com.dabaotv.vip.parse.web;

import com.dabaotv.vip.parse.Service.ParseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 周子斐
 * parse
 * @date 2020/7/23
 * @Description
 */
@RestController
@Slf4j
public class parseAPI {

    @Autowired
    private ParseService parseService;

    @GetMapping("parse")
    public String parseUrl(String url){
        if(url.contains(".html")){
            int html = url.lastIndexOf("html");
            url = url.substring(0, html) + "html";
        }
        return parseService.parseUrl(url);
    }
}
