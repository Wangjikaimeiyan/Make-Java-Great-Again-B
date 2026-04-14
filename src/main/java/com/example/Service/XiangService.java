package com.example.Service;

import com.example.Pojo.Dish;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.math.BigDecimal;
import java.util.List;

public interface XiangService {
    //    查询所川菜品
    List<Dish> searchAllDishes();
    //     ai分析菜品
    void aiChat(String question, SseEmitter sseEmitter);
    //      查询单个菜品
    Dish searchDish(Integer id);
    //      新增菜品
    void addDish(Dish dish);
    //      修改菜品
    void updateDish(Dish dish);
    //      删除菜品
    void deleteDish(Integer id);
    //      条件查询
    List<Dish> searchDishes(String name, BigDecimal price);
}
