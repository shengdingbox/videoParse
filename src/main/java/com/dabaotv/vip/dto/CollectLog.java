package com.dabaotv.vip.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 周子斐
 * videoutil
 * @date 2020/11/25
 * @Description
 */
@Table("collect_log")
@Data
public class CollectLog {

    @Id
    private int id;
    private String url;
    private String token;
    private String status;
    private String errorId;
    private String date;

}
