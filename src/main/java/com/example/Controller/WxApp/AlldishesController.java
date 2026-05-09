package com.example.Controller.WxApp;


import com.example.Pojo.Dish;
import com.example.Pojo.Result;
import com.example.Service.WxApp.AlldishesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@Tag(name = "微信菜品管理接口")
@RequestMapping("/WxUser")
public class AlldishesController {
    @Autowired
    private AlldishesService alldishesService;
//    查询所有菜品
    @GetMapping("/All/dishes")
    public Result searchAllDishes(){
        log.info("查询所有菜品");
        List<Dish> dishes = alldishesService.searchAllDishes();
        return Result.success(dishes);
    }
}
