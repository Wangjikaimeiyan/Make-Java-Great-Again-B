package com.example.Pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单主表
 */
@Data
public class Orders {
    private String userId;
    private String orderId;
    private BigDecimal allPrice;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime payTime;
    private String remark;
}

