package com.dabaotv.vip.parse.repository;

import com.dabaotv.vip.parse.dto.VideoUrl;
import org.springframework.data.repository.CrudRepository;

/**
 * @author 周子斐
 * parse
 * @date 2020/7/24
 * @Description
 */
public interface VideoRepository extends CrudRepository<VideoUrl,Integer> {
}
