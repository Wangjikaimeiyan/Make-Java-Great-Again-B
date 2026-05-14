package com.example.Service.WxApp;

import com.example.Pojo.Dish;
import com.example.Pojo.Result;

import java.util.List;

public interface AlldishesService {
    public List<Dish> searchAllDishes();
//    根据id查询
    public Dish searchDishById(Integer id);
}
