package com.dabaotv.vip.parse.util;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
public class Queues {


    /**
     * 券流水记录
     */
    public static final String SAVE = "video.save";


    @Bean
    public Queue coupFlowOperation() {
        return new Queue(SAVE);
    }

}
