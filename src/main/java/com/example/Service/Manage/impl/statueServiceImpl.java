package com.example.Service.Manage.impl;

import com.example.Service.Manage.StatueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class statueServiceImpl implements StatueService {

    @Autowired
    private RedisTemplate redisTemplate;

    //    修改营业状态
    @Override
    public void updateStatue(Integer statue) {
//        设置营业状态
        log.info("✅ 营业状态修改成功");
        redisTemplate.opsForValue().set("statue", statue, 86400, TimeUnit.SECONDS);
    }

    //    查询营业状态
    @Override
    public Integer getShopStatue() {
        Integer statue = (Integer) redisTemplate.opsForValue().get("statue");
        // 不存在默认 0 停业 / 自行改成1
        log.info("✅ 营业状态查询成功");
        return statue == null ? 0 : statue;
    }

//
}
