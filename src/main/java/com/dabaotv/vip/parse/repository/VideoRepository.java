package com.dabaotv.vip.parse.repository;

import com.dabaotv.vip.parse.dto.VideoUrl;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author 周子斐
 * parse
 * @date 2020/7/24
 * @Description
 */
public interface VideoRepository extends CrudRepository<VideoUrl,Integer> {

    @Query("SELECT * from video_url WHERE original_url =:url ")
    VideoUrl findVideoByUrl(@Param("url") String url);
}
