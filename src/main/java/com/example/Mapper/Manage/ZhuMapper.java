package com.example.Mapper.Manage;

import com.example.Pojo.Dish;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface ZhuMapper {
    // 查询所有主食 category=4
    @Select("SELECT d.id,d.name,d.price,d.detail,d.image,d.category,d.create_time,d.update_time,IFNULL(s.sale_count,0) sales " +
            "FROM dish d " +
            "LEFT JOIN dish_sale s ON d.id = s.dish_id " +
            "WHERE d.category = 4")
    List<Dish> searchAllDishes();

    // 查询单个
    @Select("select id,name,price,detail,image,category,create_time,update_time from dish where id = #{id}")
    Dish searchDish(Integer id);

    // AI用查询
    @Select("SELECT d.id,d.name,d.price,d.detail,d.image,d.category,d.create_time,d.update_time,IFNULL(s.sale_count,0) sales " +
            "FROM dish d " +
            "LEFT JOIN dish_sale s ON d.id = s.dish_id " +
            "WHERE d.category = 4")
    List<Dish> searchAllDishesforAI();

    // 新增（XML实现）
    void addDish(Dish dish);

    // 修改（XML实现）
    void updateDish(Dish dish);

    // 删除（XML实现）
    void deleteDish(Integer id);

    // 条件搜索（XML实现）
    List<Dish> searchDishes(String name, BigDecimal price);
}