package com.example.Service.WxApp;

import com.example.Pojo.OrderSubmitDTO;

public interface WxOrder {
//    下单逻辑
    String order(OrderSubmitDTO dto);
}
