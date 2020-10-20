package com.dabaotv.vip.parse.web;

import com.dabaotv.vip.parse.util.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 周子斐
 * parse
 * @date 2020/8/7
 * @Description
 */
public class DEJI {
    public static void main(String[] args) throws Exception {

        getList(null,null);
        //WBLogin("17611555590","zhouzifei");

    }

    private static String WBLogin(String username,String password) throws Exception {
        String url = "https://passport.weibo.cn";
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        Map<String, String> hears = new HashMap<>();
        hears.put("Content-Type", "application/x-www-form-urlencoded");
        hears.put("ua","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.105 Safari/537.36");
        hears.put("Referer", "https://passport.weibo.cn/signin/login?entry=mweibo&r=http%3A%2F%2Fm.weibo.cn");
        HttpResponse post = HttpUtils.doPost(url, "/sso/login", "POST", hears, null, map);
        String s = EntityUtils.toString(post.getEntity());
        return s;
    }
    private static String getList(String username,String password) throws Exception {
        String url = "https://m.weibo.cn";
        //chaohua_list = 'https://m.weibo.cn/api/container/getIndex?containerid=100803_-_page_my_follow_super'
        //        resp_list = self.session.get(chaohua_list).json()
        Map<String, String> map = new HashMap<>();
        map.put("containerid", "100803_-_followsuper");
        Map<String, String> hears = new HashMap<>();
        hears.put("Content-Type", "application/x-www-form-urlencoded");
        hears.put("ua","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.105 Safari/537.36");
        hears.put("Cookie", "SINAGLOBAL=2395581408535.976.1595243114257; _s_tentry=www.techweb.com.cn; Apache=9313896588719.52.1596507681075; ULV=1596507681079:3:1:1:9313896588719.52.1596507681075:1596017621336; login_sid_t=cc34e7956f61fce49af1f0f4a7f311a7; cross_origin_proto=SSL; UOR=blog.huiwei13.cn,widget.weibo.com,www.baidu.com; SCF=AoWxltlmFTfSjyh3HABluj_nQ5W3svUfyzXfrkL89tFBEwjZ98OIbbwCvIOatOZS1pdmyKscC852ZeOu8yEBnG0.; SUB=_2A25yKX-nDeRhGeBO6FAU-CfJzj-IHXVRX9ZvrDV8PUNbmtAKLUH3kW9NSgK6r1WnYVLyCv4Hjk38QFhoR4leX3Wi; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFYB-WZuUhpEkBnNpgg8Lo25JpX5KzhUgL.Foq7e0zf1h.fSKe2dJLoIEXLxKqL1heL1h-LxKnLB-qL1-BLxKqLB-qL1KzLxK.LBonLBK2LxKqLBo2LBKBt; SUHB=0D-FtMe_CH2p5m; ALF=1628324726; SSOLoginState=1596788727; wvr=6; webim_unReadCount=%7B%22time%22%3A1596791028458%2C%22dm_pub_total%22%3A18%2C%22chat_group_client%22%3A6203%2C%22chat_group_notice%22%3A0%2C%22allcountNum%22%3A6221%2C%22msgbox%22%3A0%7D");
        HttpResponse post = HttpUtils.doGet(url, "/api/container/getIndex", "GET", hears, map);
        String s = EntityUtils.toString(post.getEntity());
        return s;
    }
}
