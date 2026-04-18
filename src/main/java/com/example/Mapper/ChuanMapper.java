package com.example.Mapper;


import com.example.Pojo.Dish;
import com.example.annotation.AutoFill;
import com.example.enumrtation.OperationType;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface ChuanMapper {
    //查询所有川菜
    @Select("select id,name,price,detail,image,category,create_time,update_time from dish where category = 1")
    List<Dish> searchAllDishes();

    //      查询单个菜品
    @Select("select id,name,price,detail,image,category,create_time,update_time from dish where id = #{id}")
    Dish searchDish(Integer id);

//    查询所有菜品
    @Select("select name,price,detail from dish where category = 1")
    List<Dish> searchAllDishesforAI();

    //      新增菜品
    @Insert("insert into dish(name,price,detail,image,category,create_time,update_time) values(#{name},#{price},#{detail},#{image},#{category},#{createTime},#{updateTime})")
    void addDish(Dish dish);

    //      修改菜品
    @AutoFill(value = OperationType.UPDATE)
//    做约定：第一个参数是数据库表对应的实体类对象
    void updateDish(Dish dish);

    //      删除菜品
    @Delete("delete from dish where id = #{id}")
    void deleteDish(Integer id);

    //      搜索菜品
    List<Dish> searchDishes(String name, BigDecimal price);
}
