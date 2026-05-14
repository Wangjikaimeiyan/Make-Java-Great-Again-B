package com.example.Service.WxApp.Impl;

import com.example.Mapper.WxApp.AlldishesMapper;
import com.example.Pojo.Dish;
import com.example.Service.WxApp.AlldishesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AlldishesServiceImpl implements AlldishesService {
    @Autowired
    private AlldishesMapper alldishesMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

//    查询所有菜品
    @Override
    @Cacheable(value = "Dish", key = "'all'")
    public List<Dish> searchAllDishes() {
//      调用Mapper层
        log.info("查询所有菜品");
//        优先查询redis，没有则查询数据库,采用字符串存储即可
//        String key = "allDishes";
//        List<Dish> dishes = (List<Dish>) redisTemplate.opsForValue().get(key);
//        判断有无数据，有数据则返回，
//        if (dishes != null && dishes.size() > 0){
//            log.info("redis有数据");
//            return dishes;
//        }
//        无数据则使用sql，添加到redis
        List<Dish> dishes = alldishesMapper.searchAllDishes();
//        log.info("redis无数据");
//        redisTemplate.opsForValue().set(key, dishes);
        return dishes;
    }

    @Override
    @Cacheable(value = "Dish", key = "#id")
    public Dish searchDishById(Integer id) {
        log.info("查询菜品id为{}的菜品", id);
        Dish dish = alldishesMapper.searchDishById(id);
//        存入redis
        return dish;
    }
}
