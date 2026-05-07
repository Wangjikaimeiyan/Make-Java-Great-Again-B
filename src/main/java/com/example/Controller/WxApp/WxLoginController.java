package com.example.Controller.WxApp;

import com.alibaba.fastjson.JSONObject;
import com.example.Pojo.Result;
import com.example.Pojo.WxLoginDTO;
import com.example.Utils.JwtUtils;
import com.example.Utils.WxCodeCheck;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import java.util.HashMap;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@Slf4j
@RestController
@Tag(name = "微信登录接口")
public class WxLoginController {
    @Autowired
    private RestTemplate restTemplate;

    // 微信登录
    @PostMapping("/WxUser/Login")
    public Result wxLogin(@RequestBody WxLoginDTO dto) {
        log.info("微信登录");
        String code = dto.getCode();
        log.info("code:{}", code);

        // 工具类，拿到openid
        String openid = WxCodeCheck.getOpenidByCode(code, restTemplate);

        if (openid == null) {
            return Result.error("微信授权失败，请重试");
        }
        log.info("用户openid:{}", openid);
        // 封装claim
        Map<String, Object> claims = new HashMap<>();
        claims.put("openid", openid);
//        生成JWT令牌
        String token = JwtUtils.generateJwt(claims);
        log.info("token:{}", token);
        return Result.success(token);
    }


}
