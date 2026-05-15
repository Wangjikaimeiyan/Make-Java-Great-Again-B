package com.example.Mapper.Manage;


import com.example.Pojo.Dish;
import com.example.annotation.AutoFill;
import com.example.enumrtation.OperationType;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface ChuanMapper {
    //查询所有川菜
    @Select("SELECT d.id,d.name,d.price,d.detail,d.image,d.category,d.create_time,d.update_time,IFNULL(s.sale_count,0) sales " +
            "FROM dish d " +
            "LEFT JOIN dish_sale s ON d.id = s.dish_id " +
            "WHERE d.category = 1")
    List<Dish> searchAllDishes();


    //      查询单个菜品
    @Select("select id,name,price,detail,image,category,create_time,update_time from dish where id = #{id}")
    Dish searchDish(Integer id);

    //    查询所有菜品
    @Select("SELECT d.id,d.name,d.price,d.detail,d.image,d.category,d.create_time,d.update_time,IFNULL(s.sale_count,0) sales " +
            "FROM dish d " +
            "LEFT JOIN dish_sale s ON d.id = s.dish_id " +
            "WHERE d.category = 1")
    List<Dish> searchAllDishesforAI();

    //      新增菜品
    void addDish(Dish dish);

    //      修改菜品
    @AutoFill(value = OperationType.UPDATE)
//    做约定：第一个参数是数据库表对应的实体类对象
    void updateDish(Dish dish);

    //      删除菜品
    void deleteDish(Integer id);


    //      搜索菜品
    List<Dish> searchDishes(String name, BigDecimal price);

    //    销量
// 初始化销量0，是INSERT不是UPDATE！
    @Insert("INSERT INTO dish_sale(dish_id,sale_count) VALUES(#{id},#{count})")
    void addSaleCount(@Param("id") Integer id, @Param("count") Integer count);

}
