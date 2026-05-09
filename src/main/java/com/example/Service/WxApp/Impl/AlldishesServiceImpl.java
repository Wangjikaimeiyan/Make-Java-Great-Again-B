package com.example.Service.WxApp.Impl;

import com.example.Mapper.WxApp.AlldishesMapper;
import com.example.Pojo.Dish;
import com.example.Service.WxApp.AlldishesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AlldishesServiceImpl implements AlldishesService {
    @Autowired
    private AlldishesMapper alldishesMapper;

//    查询所有菜品
    @Override
    public List<Dish> searchAllDishes() {
//      调用Mapper层
        log.info("查询所有菜品");
        List<Dish> dishes = alldishesMapper.searchAllDishes();
        return dishes;
    }
}
