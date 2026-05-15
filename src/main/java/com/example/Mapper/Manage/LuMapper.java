package com.example.Mapper.Manage;

import com.example.Pojo.Dish;
import com.example.annotation.AutoFill;
import com.example.enumrtation.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface LuMapper {
    //查询所有鲁菜 category=3
    @Select("SELECT d.id,d.name,d.price,d.detail,d.image,d.category,d.create_time,d.update_time,IFNULL(s.sale_count,0) sales " +
            "FROM dish d " +
            "LEFT JOIN dish_sale s ON d.id = s.dish_id " +
            "WHERE d.category = 3")
    List<Dish> searchAllDishes();

    //      查询单个菜品
    @Select("select id,name,price,detail,image,category,create_time,update_time from dish where id = #{id}")
    Dish searchDish(Integer id);

    //    查询所有菜品 AI用
    @Select("SELECT d.id,d.name,d.price,d.detail,d.image,d.category,d.create_time,d.update_time,IFNULL(s.sale_count,0) sales " +
            "FROM dish d " +
            "LEFT JOIN dish_sale s ON d.id = s.dish_id " +
            "WHERE d.category = 3")
    List<Dish> searchAllDishesforAI();

    //      新增菜品 XML实现
    void addDish(Dish dish);

    //      修改菜品 保留自动填充注解
    @AutoFill(value = OperationType.UPDATE)
    void updateDish(Dish dish);

    //      删除菜品 XML实现
    void deleteDish(Integer id);

    //      搜索菜品 XML实现
    List<Dish> searchDishes(String name, BigDecimal price);

    //初始化销量插入，和ChuanMapper完全一致
    @Insert("INSERT INTO dish_sale(dish_id,sale_count) VALUES(#{id},#{count})")
    void addSaleCount(@Param("id") Integer id, @Param("count") Integer count);
}
