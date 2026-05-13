package com.example.Service.Manage.impl;

import com.example.Mapper.Manage.ChuanMapper;
import com.example.Pojo.Dish;
import com.example.Service.Manage.ChuanService;
import com.example.Utils.CleanCache;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class ChuanServiceImpl implements ChuanService {
    @Autowired
    private ChuanMapper chuanMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ChatClient chatClient;
    @Autowired
    private CleanCache cleanCache;

    //  查询全部川菜
    @Override
    public List<Dish> searchAllDishes() {
        log.info("查询所有川菜"+"ChuanServiceImpl");
        return chuanMapper.searchAllDishes();
    }


    @Override
    public void aiChat(String question, SseEmitter sseEmitter) {
            try {
                String text = "以上是所有菜品,现在你是一位专业的川菜AI助手，现在被部署在了餐厅点菜系统里面，你的任务就是" +
                        "给顾客介绍菜品。 回答要求： 1. 回答最好四川方言风格，可爱俏皮风格 2. 介绍菜品时候说明名称，详细信息，推荐理由 3. 需要更能出抓住顾客的心4.简短回答,自带温度，触动人心，引人共情 ";
//                1.调用数据库里面的数据。一并传递
                List<Dish> dishes = chuanMapper.searchAllDishesforAI();
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
        log.info("查询单个菜品"+"ChuanServiceImpl");
        Dish dish = chuanMapper.searchDish(id);
        return dish;
    }

//    新增菜品
    @Override
    @Transactional(rollbackFor = {Exception.class})
    @CacheEvict(value = "Dish", key = "'all'")
    public void addDish(Dish dish) {
        log.info("新增菜品"+"ChuanServiceImpl");
        chuanMapper.addDish(dish);
//        清空redis缓存
//        cleanCache.cleanCache("Dish::all");
//        throw new RuntimeException("测试事务");
        log.info("新增菜品成功"+"ChuanServiceImpl");
    }

    //      修改菜品
    @Override
    @Transactional(rollbackFor = {Exception.class})
    @CacheEvict(value = "Dish", key = "'all'")
    public void updateDish(Dish dish) {
        log.info("修改菜品" + "ChuanServiceImpl");
        chuanMapper.updateDish(dish);
//        清空redis缓存
//        cleanCache.cleanCache("Dish::all");
        log.info("修改菜品成功" + "ChuanServiceImpl");
    }

//    删除菜品
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void deleteDish(Integer id) {
        log.info("删除菜品"+"ChuanServiceImpl");
        chuanMapper.deleteDish(id);
        //        清空redis缓存
        cleanCache.cleanCache("Dish::all");
        log.info("删除菜品成功"+"ChuanServiceImpl");
    }

//    条件搜索
    @Override
    public List<Dish> searchDishes(String name, BigDecimal price) {
//        价格是最高价
        log.info("条件搜索"+"ChuanServiceImpl");
        List<Dish> dishes = chuanMapper.searchDishes(name,price);
        log.info("条件搜索成功"+"ChuanServiceImpl");
        return dishes;
    }
}











































