package com.example.Mapper.Manage;

import com.example.Pojo.Dish;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface ZhuMapper {
    // 查询所有主食 category=4
    @Select("select id,name,price,detail,image,category,create_time,update_time from dish where category = 4")
    List<Dish> searchAllDishes();

    // 查询单个
    @Select("select id,name,price,detail,image,category,create_time,update_time from dish where id = #{id}")
    Dish searchDish(Integer id);

    // AI用查询
    @Select("select name,price,detail from dish where category = 4")
    List<Dish> searchAllDishesforAI();

    // 新增
    @Insert("insert into dish(name,price,detail,image,category,create_time,update_time) " +
            "values(#{name},#{price},#{detail},#{image},#{category},#{createTime},#{updateTime})")
    void addDish(Dish dish);

    // 修改
    void updateDish(Dish dish);

    // 删除
    @Delete("delete from dish where id = #{id}")
    void deleteDish(Integer id);

    // 条件搜索
    List<Dish> searchDishes(String name, BigDecimal price);
}
