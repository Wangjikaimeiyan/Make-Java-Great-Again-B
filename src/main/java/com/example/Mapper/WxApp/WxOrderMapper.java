package com.example.Mapper.WxApp;

import com.example.Pojo.OrderDetails;
import com.example.Pojo.Orders;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface WxOrderMapper {
    /**
     * 插入订单
     * @param order
     */
    void insertOrder(Orders order);

    void insertOrderDetails(ArrayList<OrderDetails> details);
}
