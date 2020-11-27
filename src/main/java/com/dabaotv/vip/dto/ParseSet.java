package com.dabaotv.vip.dto;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author 周子斐
 * @date 2020/11/25
 * @Description
 */
@Table("parse_set")
@Data
public class ParseSet {
    private String macUrl;
    private String macName;
    private String macPwd;
    private String parseUid;
    private String parseToken;
}
