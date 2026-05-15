package com.example.Controller.WxApp;

import com.example.Pojo.OrderSubmitDTO;
import com.example.Pojo.Result;
import com.example.Service.WxApp.WxOrder;
import com.example.Utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//微信订单管理接口
@Tag(name = "微信订单管理接口")
@RestController
@RequestMapping("/WxUser")
@Slf4j
public class WxOrderController {
    @Autowired
    private WxOrder wxOrder;


//    下单逻辑
    @PostMapping("/Order")
//    public Result order(String openid, String remark, Map<Integer, Integer> dishidCounts){
    public Result order(@RequestBody OrderSubmitDTO dto, HttpServletRequest  request){
        log.info("下单");
        if (dto.getRemark() == null || dto.getRemark().isEmpty()){
            dto.setRemark("无");
        }
        String token = request.getHeader("token");
        String openid;
        try {
            Claims claims = JwtUtils.parseJWT(token);
            openid = claims.get("openid", String.class);
            log.info("openid:{}",openid);
            if (openid == null|| openid.isEmpty()){
                return Result.error("无效凭证");
            }
            dto.setOpenid(openid);
        } catch (Exception e){
            return Result.error("登录过期或者无效凭证");
        }
        log.info("{}{}{}",dto.getOpenid(),dto.getRemark(),dto.getDishidCounts());
//        调用Service层下单逻辑
        return (wxOrder.order(dto));
    }
//    TODO: 订单查询逻辑


//    TODO: 订单取消逻辑
}
