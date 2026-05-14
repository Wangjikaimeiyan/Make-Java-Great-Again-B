package com.example.Pojo;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class OrderDetails {
    private String orderId;
    private Integer dishId;
    private BigDecimal price;
    private Integer num;
    private BigDecimal subtotal;
}

