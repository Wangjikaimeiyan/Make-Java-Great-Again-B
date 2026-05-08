package com.example.Pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WxUser {
    private Integer id;
    private String nickName;
    private String url;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String openid;
//    设置黑名单状态0：正常 1：黑名单
    private Integer status;
}
