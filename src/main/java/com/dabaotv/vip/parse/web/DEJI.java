package com.dabaotv.vip.parse.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dabaotv.vip.parse.util.HttpData;
import com.dabaotv.vip.parse.util.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.omg.CORBA.OBJ_ADAPTER;
import org.omg.PortableInterceptor.USER_EXCEPTION;

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

        //System.out.println(getList());
        //WBLogin("17611555590","zhouzifei");
        destory("6046088637");

    }

    private static String WBLogin(String username, String password) throws Exception {
        String url = "https://passport.weibo.cn";
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        map.put("savestate", "1");
        map.put("r", "https://m.weibo.cn/");
        map.put("ec", "0");
        map.put("pagerefer", "https://m.weibo.cn/");
        map.put("entry", "mweibo");
        map.put("wentry", "");
        map.put("loginfrom", "");
        map.put("client_id", "");
        map.put("code", "");
        map.put("qq", "");
        map.put("mainpageflag:", "1");
        map.put("hff", "");
        map.put("hfp", "");
        Map<String, String> hears = new HashMap<>();
        hears.put("Content-Type", "application/x-www-form-urlencoded");
        hears.put("User-Agent", "jdapp;android;8.4.2;8.0.0;;network/wifi;model/Mi Note 2;osVer/26;appBuild/71043;psn/|7;psq/1;uid/;adk/;ads/;pap/JA2015_311210|8.4.2|ANDROID 8.0.0;osv/8.0.0;pv/2.23;jdv/;ref/com.jingdong.app.mall.WebActivity;partner/huawei;apprpd/Home_Main;Mozilla/5.0 (Linux; Android 8.0.0; Mi Note 2 Build/OPR1.170623.032; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/71.0.3578.99 Mobile Safari/537.36");
        hears.put("Referer", "https://passport.weibo.cn/signin/login?entry=mweibo&res=wel&wm=3349&r=https%3A%2F%2Fm.weibo.cn%2F");
        HttpResponse post = HttpUtils.doPost(url, "/sso/login", "POST", hears, null, map);
        String s = EntityUtils.toString(post.getEntity());
        return s;
    }

    private static String getList(String username, String password) throws Exception {
        String url = "https://m.weibo.cn";
        //chaohua_list = 'https://m.weibo.cn/api/container/getIndex?containerid=100803_-_page_my_follow_super'
        //        resp_list = self.session.get(chaohua_list).json()
        Map<String, String> map = new HashMap<>();
        map.put("containerid", "100803_-_followsuper");
        Map<String, String> hears = new HashMap<>();
        hears.put("Content-Type", "application/x-www-form-urlencoded");
        hears.put("ua", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.105 Safari/537.36");
        hears.put("Cookie", "SINAGLOBAL=2395581408535.976.1595243114257; _s_tentry=www.techweb.com.cn; Apache=9313896588719.52.1596507681075; ULV=1596507681079:3:1:1:9313896588719.52.1596507681075:1596017621336; login_sid_t=cc34e7956f61fce49af1f0f4a7f311a7; cross_origin_proto=SSL; UOR=blog.huiwei13.cn,widget.weibo.com,www.baidu.com; SCF=AoWxltlmFTfSjyh3HABluj_nQ5W3svUfyzXfrkL89tFBEwjZ98OIbbwCvIOatOZS1pdmyKscC852ZeOu8yEBnG0.; SUB=_2A25yKX-nDeRhGeBO6FAU-CfJzj-IHXVRX9ZvrDV8PUNbmtAKLUH3kW9NSgK6r1WnYVLyCv4Hjk38QFhoR4leX3Wi; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFYB-WZuUhpEkBnNpgg8Lo25JpX5KzhUgL.Foq7e0zf1h.fSKe2dJLoIEXLxKqL1heL1h-LxKnLB-qL1-BLxKqLB-qL1KzLxK.LBonLBK2LxKqLBo2LBKBt; SUHB=0D-FtMe_CH2p5m; ALF=1628324726; SSOLoginState=1596788727; wvr=6; webim_unReadCount=%7B%22time%22%3A1596791028458%2C%22dm_pub_total%22%3A18%2C%22chat_group_client%22%3A6203%2C%22chat_group_notice%22%3A0%2C%22allcountNum%22%3A6221%2C%22msgbox%22%3A0%7D");
        HttpResponse post = HttpUtils.doGet(url, "/api/container/getIndex", "GET", hears, map);
        String s = EntityUtils.toString(post.getEntity());
        return s;
    }

    private static String getList() throws Exception {
        String url = "https://weibo.com";
        Map<String, String> map = new HashMap<>();
        map.put("page", "1");
        map.put("uid", "6032589553");
        Map<String, String> hears = new HashMap<>();
        hears.put("Content-Type", "application/json; charset=utf-8");
        hears.put("ua", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.105 Safari/537.36");
        hears.put("Cookie", "SINAGLOBAL=1825325488201.5967.1601800238673; SCF=ArjWMMJqKqP4wtPIskQnw7attUh4B3FusKtzCNKevXIfogrghFr6kufxLXagOgkRzZ7m70mDTWvucpV5N848O5A.; login_sid_t=2bd61bff178b7dd07adf8f329808b082; cross_origin_proto=SSL; _s_tentry=www.baidu.com; UOR=,,www.baidu.com; wb_view_log=1680*10502; Apache=4929307745172.518.1604478959716; ULV=1604478959721:4:1:1:4929307745172.518.1604478959716:1603433988136; SUB=_2A25yphhaDeRhGeBO6FAU-CfJzj-IHXVR0g6SrDV8PUNbmtAKLRnBkW9NSgK6r1sUsmFvbu7FBUlDqkhnSAwoFocQ; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFYB-WZuUhpEkBnNpgg8Lo25JpX5KzhUgL.Foq7e0zf1h.fSKe2dJLoIEXLxKqL1heL1h-LxKnLB-qL1-BLxKqLB-qL1KzLxK.LBonLBK2LxKqLBo2LBKBt; ALF=1636014986; SSOLoginState=1604478986; wvr=6; wb_view_log_6032589553=1680*10502; webim_unReadCount=%7B%22time%22%3A1604480323363%2C%22dm_pub_total%22%3A13%2C%22chat_group_client%22%3A190890%2C%22chat_group_notice%22%3A0%2C%22allcountNum%22%3A190919%2C%22msgbox%22%3A0%7D; XSRF-TOKEN=EWzuqVSjgUFIkiAd5nbAwMzG; WBPSESS=Pb-XzZoa7intFwm_2uBkbwb1kXPt5Ysj6jq_CSBCJNCBn8yDar0f2UFQnS_A8DcJltTqh3NXpjA_Ic5vFNxomjcdYLmkRIZd4l73po8ynYNiM_5lnL8aVTMBQPXR5tJG");
        hears.put("referer", "https://weibo.com/u/page/follow/6032589553");
        HttpResponse post = HttpUtils.doGet(url, "/ajax/friendships/friends", "GET", hears, map);
        String s = EntityUtils.toString(post.getEntity());
        JSONObject jsonObject = JSONObject.parseObject(s);
        Object users = jsonObject.get("users");
        JSONArray objects = (JSONArray) users;
        for (Object object : objects) {
            JSONObject jsonObject1 = (JSONObject) object;
            if (!(Boolean) (jsonObject1.get("follow_me"))) {
                System.out.println(jsonObject1.get("idstr"));
            }
        }
        return s;
    }

    private static String destory(String uid) throws Exception {
        String url = "https://weibo.com";
        //chaohua_list = 'https://m.weibo.cn/api/container/getIndex?containerid=100803_-_page_my_follow_super'
        //        resp_list = self.session.get(chaohua_list).json()
        Map<String, String> param = new HashMap<>();
        Map<String, String> hears = new HashMap<>();
        hears.put("Cookie", "SINAGLOBAL=2395581408535.976.1595243114257; _s_tentry=www.techweb.com.cn; Apache=9313896588719.52.1596507681075; ULV=1596507681079:3:1:1:9313896588719.52.1596507681075:1596017621336; login_sid_t=cc34e7956f61fce49af1f0f4a7f311a7; cross_origin_proto=SSL; UOR=blog.huiwei13.cn,widget.weibo.com,www.baidu.com; SCF=AoWxltlmFTfSjyh3HABluj_nQ5W3svUfyzXfrkL89tFBEwjZ98OIbbwCvIOatOZS1pdmyKscC852ZeOu8yEBnG0.; SUB=_2A25yKX-nDeRhGeBO6FAU-CfJzj-IHXVRX9ZvrDV8PUNbmtAKLUH3kW9NSgK6r1WnYVLyCv4Hjk38QFhoR4leX3Wi; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFYB-WZuUhpEkBnNpgg8Lo25JpX5KzhUgL.Foq7e0zf1h.fSKe2dJLoIEXLxKqL1heL1h-LxKnLB-qL1-BLxKqLB-qL1KzLxK.LBonLBK2LxKqLBo2LBKBt; SUHB=0D-FtMe_CH2p5m; ALF=1628324726; SSOLoginState=1596788727; wvr=6; webim_unReadCount=%7B%22time%22%3A1596791028458%2C%22dm_pub_total%22%3A18%2C%22chat_group_client%22%3A6203%2C%22chat_group_notice%22%3A0%2C%22allcountNum%22%3A6221%2C%22msgbox%22%3A0%7D");
        hears.put(":authority", " weibo.com");
        hears.put(":method", "POST");
        hears.put(":path", "/ajax/friendships/destory");
        hears.put(":scheme", "https");
        hears.put("accept", "application/json, text/plain, */*");
        hears.put("accept-encoding", "gzip, deflate, br");
        hears.put("accept-language", "zh-CN,zh;q=0.9,en;q=0.8");
        hears.put("cache-control", "no-cache");
        //hears.put("content-length", "2000");
        hears.put("content-type", "application/json;charset=UTF-8");
        hears.put("origin", "https://weibo.com");
        hears.put("pragma", "no-cache");
        hears.put("referer", "https://weibo.com/u/page/follow/6032589553");
        hears.put("sec-fetch-dest", "empty");
        hears.put("sec-fetch-mode", "cors");
        hears.put("sec-fetch-site", "same-origin");
        hears.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36");
        hears.put("x-requested-with", "XMLHttpRequest");
        hears.put("x-xsrf-token", "4gVabfo3kRyPaO-BG3psJEkw");
        HttpResponse post = HttpUtils.doPost(url, "/ajax/friendships/destory", "POST", hears, param, "{\"uid\":"+uid+"}");
        String s = EntityUtils.toString(post.getEntity());
        return s;
    }
}
