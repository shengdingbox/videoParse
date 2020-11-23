package com.dabaotv.vip.parse.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

/**
 * @author 周子斐
 * parse
 * @date 2020/7/23
 * @Description
 */
@Data
@Table("video_url")
public class VideoUrl implements Serializable {
    @Id
    private int id;
    //原始来源
    private String metareferer;
    //状态码
    private String code;
    private String parser;
    private String success;
    private String type;
    private String url;
    private String flash;
    private String player;
    private String domain;
    private String originalUrl;
    private String prefixUrl;
    private String prefixType;
}
