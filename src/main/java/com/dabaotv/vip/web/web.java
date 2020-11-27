package com.dabaotv.vip.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 周子斐
 * @date 2020/11/27
 * @Description
 */
@Controller
public class web {

    @RequestMapping("/index.html")
    public String add(){
        return "index";
    }
}
