package com.dabaotv.vip.repository;


import com.dabaotv.vip.dto.ParseSet;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author 周子斐
 * parse
 * @date 2020/7/24
 * @Description
 */
public interface ParseSetRepository extends CrudRepository<ParseSet, Integer> {

    @Query("SELECT * from parse_set limit 1 ")
    ParseSet getSysParam();
}
