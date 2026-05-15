package com.example.Service.WxApp.Impl;

import com.example.Mapper.WxApp.WxOrderMapper;
import com.example.Pojo.*;
import com.example.Service.WxApp.AlldishesService;
import com.example.Service.WxApp.WxOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class WxOrderImpl implements WxOrder {
    @Autowired
    private AlldishesService alldishesService;
    @Autowired
    private WxOrderMapper wxOrderMapper;
    @Override
    @Transactional//事务
    public Result order(OrderSubmitDTO dto) {
//        解析订单信息
        log.info("解析订单信息");
        String openid = dto.getOpenid();// 用户openid
        String remark = dto.getRemark();// 订单备注
        Map<String, Integer> dishidCounts = dto.getDishidCounts();// 菜品id和数量
//        生成UUID
        String orderId = UUID.randomUUID().toString();
//        接下来分为两步，1计算详情2生成订单
//        首先解析dishidCounts,定义一个集合，用于存储详情
        ArrayList<OrderDetails> details = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : dishidCounts.entrySet()) {
            OrderDetails orderDetails = new OrderDetails();// 创建订单详情对象
            orderDetails.setOrderId(orderId);// 订单id
            String dishid = entry.getKey();// 菜品id
            orderDetails.setDishId(Integer.parseInt(dishid));// 菜品id
            Integer count = entry.getValue();// 数量
            orderDetails.setNum(count);// 数量
//          获取菜的单价,根据菜品id查询
            Dish dish = alldishesService.searchDishById(Integer.parseInt(dishid));// 获取菜的全部信息
            if(dish == null){
//                手动回滚事务
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.error("菜品不存在");
            }
            BigDecimal price = dish.getPrice();// 单价
            orderDetails.setPrice(price);// 单价
            BigDecimal subtotal = price.multiply(new BigDecimal(count));// 小计
            orderDetails.setSubtotal(subtotal);// 小计
            orderDetails.setDishName(dish.getName());// 菜品名称
            orderDetails.setDishImg(dish.getImage());// 菜品图片
            log.info("菜品id:{}，数量:{},价格{}", dishid, count, price);
            //        将orderDetails添加到集合中
            details.add(orderDetails);
        }
//      生成订单
        log.info("生成订单");
        Orders order = new Orders();
        order.setOrderId(orderId); // 设置UUID
        order.setUserId(openid); // 用户id
        order.setAllPrice(details.stream().map(OrderDetails::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add));// 订单总价
        order.setStatus(0);// 订单状态0待支付
        order.setCreateTime(java.time.LocalDateTime.now());
        order.setRemark(remark);
//        插入订单，插入订单详情
        log.info("插入订单");
        wxOrderMapper.insertOrder(order);
        log.info("插入订单详情");
        wxOrderMapper.insertOrderDetails(details);
//        TODO:稍后改为Result，
        return Result.success("Test");
    }
}
