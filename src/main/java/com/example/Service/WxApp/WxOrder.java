package com.example.Service.WxApp;

import com.example.Pojo.OrderSubmitDTO;
import com.example.Pojo.Result;

public interface WxOrder {
//    下单逻辑
    Result order(OrderSubmitDTO dto);
}
