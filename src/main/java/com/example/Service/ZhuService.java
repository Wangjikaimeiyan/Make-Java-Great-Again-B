package com.example.Service;

import com.example.Pojo.Dish;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.math.BigDecimal;
import java.util.List;

public interface ZhuService {
    // 查询所有主食
    List<Dish> searchAllDishes();

    // AI分析主食
    void aiChat(String question, SseEmitter sseEmitter);

    // 查询单个主食
    Dish searchDish(Integer id);

    // 新增主食
    void addDish(Dish dish);

    // 修改主食
    void updateDish(Dish dish);

    // 删除主食
    void deleteDish(Integer id);

    // 条件查询主食
    List<Dish> searchDishes(String name, BigDecimal price);
}
