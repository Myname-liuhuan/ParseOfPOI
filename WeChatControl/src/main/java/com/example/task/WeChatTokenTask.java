package com.example.task;

import com.example.service.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


/**
 * 微信使用的定时任务类
 * @author huanliu
 */
@Configuration
@EnableScheduling
public class WeChatTokenTask {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    WeChatService weChatService;

    /**
     * redis心跳检测，因为长时间不使用redisTemplate 连接就会断开
     * 30秒执行一次
     */
    @Scheduled(cron = "0/30 * * * * ?")
    private void redisHeartbeat(){
        try {
            String s = (String) redisTemplate.opsForValue().get("access_token");
            System.out.println("redis连接正常"+ s);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("长时间未操作，redis连接已断开");
        }
    }

    /**
     * 每两小时重新获取一次token保存到全局变量
     */
    @Scheduled(cron = "0 0 0/2 * * ?")
    public void flushTokenValue(){
        weChatService.flushAccessToken();
    }



}
