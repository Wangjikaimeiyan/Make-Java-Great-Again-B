package com.example.Pojo;

import lombok.Data;

@Data
public class WxLoginDTO {
    // 微信登录code
    private String code;
    // 微信昵称
    private String nickName;
    // 微信头像地址
    private String url;
}

