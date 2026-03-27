package com.example.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dish {
    private int id;
    private String name;
    private BigDecimal price;
    private String detail;
    private String image;
    private int category;/*分类1234川·湘·鲁·主食*/
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
