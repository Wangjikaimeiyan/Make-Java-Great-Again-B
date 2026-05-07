package com.example.Utils;

import com.alibaba.fastjson.JSONObject;
import com.example.Config.WxProperties;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class WxCodeCheck {
    // 微信小程序配置 后续可以放配置文件读取
    public static String APPID;
    public static String APP_SECRET;

    private final WxProperties wxProperties;
    public WxCodeCheck(WxProperties wxProperties) {
        this.wxProperties = wxProperties;
    }

    @PostConstruct
    public void init() {
        APPID = wxProperties.getAppid();
        APP_SECRET = wxProperties.getSecret();
    }

    // 微信接口地址模板
    private static final String URL_FORMAT =
            "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";


    /**
     * 校验微信code，返回openid
     *
     * @param code 小程序传过来的code
     * @return openid  失败返回null
     */
    public static String getOpenidByCode(String code, RestTemplate restTemplate) {
        log.info("开始校验code:{}", code);
        // 拼接地址
        String url = String.format(URL_FORMAT, APPID, APP_SECRET, code);
        // 调用微信接口
        log.info("请求微信接口:{}", url);
        String result = restTemplate.getForObject(url, String.class);
        log.info("微信返回结果:{}", result);
        JSONObject json = JSONObject.parseObject(result);
        // 拿openid
        String openid = json.getString("openid");
        return openid;
    }

    /**
     * 重载：同时返回 openid + session_key
     */
    public static JSONObject getOpenInfoByCode(String code, RestTemplate restTemplate) {
        String url = String.format(URL_FORMAT, APPID, APP_SECRET, code);
        String result = restTemplate.getForObject(url, String.class);
        return JSONObject.parseObject(result);
    }

}

