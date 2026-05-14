package com.example.Pojo;

import lombok.Data;
import java.util.Map;

/**
 * 下单接收前端参数
 */
@Data
public class OrderSubmitDTO {
    private String openid;
    private String remark;
    private Map<String, Integer> dishidCounts;
}
