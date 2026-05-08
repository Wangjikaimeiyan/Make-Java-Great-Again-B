package com.example.Service.Manage.impl;

import com.example.Pojo.Dish;
import com.example.Mapper.Manage.LuMapper;
import com.example.Service.Manage.LuService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class LuServiceImpl implements LuService {
    @Autowired
    private LuMapper luMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ChatClient chatClient;


    //  查询全部鲁菜
    @Override
    public List<Dish> searchAllDishes() {
        log.info("查询所有鲁菜"+"LuServiceImpl");
        return luMapper.searchAllDishes();
    }


    @Override
    public void aiChat(String question, SseEmitter sseEmitter) {
        try {
            String text = "以上是所有菜品,现在你是一位专业的鲁菜AI助手，现在被部署在了餐厅点菜系统里面，你的任务就是" +
                    "给顾客介绍菜品。 回答要求： 1. 回答最好山东方言风格，可爱俏皮风格 2. 介绍菜品时候说明名称，详细信息，推荐理由 3. 需要更能抓住顾客的心4.简短回答,自带温度，触动人心，引人共情 ";
//                1.调用数据库里面的数据。一并传递
            List<Dish> dishes = luMapper.searchAllDishesforAI();
            String dishes_text = objectMapper.writeValueAsString(dishes);
            String endQuestion = dishes_text + "\n" + text +"\n"+ question;

//                2.获取流
            Flux<String> contentFlux = chatClient.prompt()
                    .user(endQuestion)
                    .stream()
                    .content();

            // 3. 订阅流，正确处理完成和错误
            contentFlux.subscribe(
                    content -> {
                        try {
                            sseEmitter.send(content);
                        } catch (Exception e) {
                            // 发送失败（如客户端断开），终止 emitter 并记录日志
                            log.warn("SSE send failed, client may be disconnected", e);
                            sseEmitter.completeWithError(e);
                            // 注意：这里无法直接取消订阅，但后续 onNext 不会再执行（因为 emitter 已关闭）
                            // 如需彻底取消，可改用自定义 Subscriber 持有 Subscription 并调用 cancel()
                        }
                    },
                    sseEmitter::completeWithError,  // 流内部发生错误
                    sseEmitter::complete            // 流正常结束
            );
        } catch (Exception e) {
            sseEmitter.completeWithError(e);
        }
    }
    //      查询单个菜品
    @Override
    public Dish searchDish(Integer id) {
        log.info("查询单个菜品"+"LuServiceImpl");
        Dish dish = luMapper.searchDish(id);
        return dish;
    }

    //    新增菜品
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void addDish(Dish dish) {
        log.info("新增菜品"+"LuServiceImpl");
        luMapper.addDish(dish);
//        throw new RuntimeException("测试事务");
        log.info("新增菜品成功"+"LuServiceImpl");
    }
    //      修改菜品
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void updateDish(Dish dish) {
        log.info("修改菜品"+"LuServiceImpl");
        luMapper.updateDish(dish);
        log.info("修改菜品成功"+"LuServiceImpl");
    }

    //    删除菜品
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void deleteDish(Integer id) {
        log.info("删除菜品"+"LuServiceImpl");
        luMapper.deleteDish(id);
        log.info("删除菜品成功"+"LuServiceImpl");
    }

    //    条件搜索
    @Override
    public List<Dish> searchDishes(String name, BigDecimal price) {
//        价格是最高价
        log.info("条件搜索"+"LuServiceImpl");
        List<Dish> dishes = luMapper.searchDishes(name,price);
        log.info("条件搜索成功"+"LuServiceImpl");
        return dishes;
    }
}
