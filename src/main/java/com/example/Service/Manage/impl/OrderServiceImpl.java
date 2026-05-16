package com.example.Service.Manage.impl;

import com.example.Mapper.Manage.OrderMapper;
import com.example.Pojo.DishesSales;
import com.example.Service.Manage.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<DishesSales> allSales() {
//        查询所有销量
        log.info("Service查询销量");
        return orderMapper.allSales();
    }
}
