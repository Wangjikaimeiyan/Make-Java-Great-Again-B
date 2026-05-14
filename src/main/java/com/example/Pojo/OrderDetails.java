package com.example.Pojo;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDetails {
    private String orderId;        // 订单号
    private Integer dishId;        // 菜品ID
    private String dishName;       // 下单时菜品名称
    private String dishImg;         // 下单时菜品图片
    private BigDecimal price;      // 下单单价
    private Integer num;            // 购买数量
    private BigDecimal subtotal;   // 小计金额
}
