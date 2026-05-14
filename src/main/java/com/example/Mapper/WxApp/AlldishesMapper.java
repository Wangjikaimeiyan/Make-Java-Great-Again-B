package com.example.Mapper.WxApp;

import com.example.Pojo.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AlldishesMapper {
    @Select("select id,name,price,detail,image,category from dish")
    List<Dish> searchAllDishes();
    @Select("select id,name,price,detail,image,category from dish where id=#{id}")
    Dish searchDishById(Integer id);
}
