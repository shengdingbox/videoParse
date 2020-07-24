package com.dabaotv.vip.parse.util;

import lombok.Data;

/**
 * @author 周子斐
 * parse
 * @date 2020/7/24
 * @Description
 */

public enum VideoType {
    XIGUA("xgsp"),
    YOUKU("youku.com"),
    TUDOU("tudou.com"),
    AQIYI("iqiyi.com"),
    MGTV("mgtv.com"),
    LETV("le.com"),
    QQ("qq.com"),
    SOHU("sohu.com"),
    PPTV("pptv.com"),
    M1905("1905.com"),
    MIGU("miguvideo.com"),
    YIYUETAI("yinyuetai.com"),
    M360("360kan.com"),
    WASU("wasu.cn"),
    CCTV("cctv.com"),
    SINA("sina.com.cn"),
    M3U8("m3u8"),
    MP4("mp4"),
    HLS("hls"),

//    开眼视频
//    http://www.eyepetizer.net/detail.html?vid=18376
//            2MM视频
//    http://e.22k.im/2a161
//    Naver네이버
//    https://tv.naver.com/v/3716396
//    糖豆视频
//    http://www.tangdou.com/v92/dAOEOYOjwT2T0Q2.html
//    梨视频
//    https://www.pearvideo.com/video_1352720
//    第一视频
//    http://www.v1.cn/video/14906477.shtml
//    汽车之家视频
//    https://v.autohome.com.cn/v-2126755.html#pvareaid=3454182
//    ECHOMV视频
//    http://www.app-echo.com/mv/1150
//    东方头条
//    http://video.eastday.com/a/180610095032571464694.html
//    西瓜视频
//            XiGua_b9893d8eb7094675bc2ef12c3ef19163
//    爆米花视频
//    https://video.baomihua.com/v/38037188
//    酷狗MV
//    http://www.kugou.com/mvweb/html/mv_664423.html
//    触手视频
//    https://chushou.tv/gamezone/video/play/4178557.htm
//    酷狗繁星MV
//    http://fanxing.kugou.com/mvplay/1133537471372865536
//    酷狗LIVE
//    http://live.kugou.com/shou/943

    ;

    private String type;

    VideoType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    }
