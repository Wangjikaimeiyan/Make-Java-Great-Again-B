package com.example.Mapper.WxApp;

import com.example.Pojo.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AlldishesMapper {
    @Select("SELECT d.id, d.name, d.price, d.detail, d.image, d.category, COALESCE(ds.sale_count, 0) as sales FROM dish d LEFT JOIN dish_sale ds ON d.id = ds.dish_id ORDER BY sales DESC, d.id ASC")
    List<Dish> searchAllDishes();


    @Select("select id,name,price,detail,image,category from dish where id=#{id}")
    Dish searchDishById(Integer id);
}
