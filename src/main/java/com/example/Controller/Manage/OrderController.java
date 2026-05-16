package com.example.Controller.Manage;

import com.example.Pojo.DishesSales;
import com.example.Pojo.Result;
import com.example.Service.Manage.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/Order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/allSales")
    @Operation(summary = "查询所有销量")
    public Result allSales(){
        log.info("查询销量");
        List<DishesSales> dishesSales = orderService.allSales();
        return Result.success(dishesSales);
    }
}
