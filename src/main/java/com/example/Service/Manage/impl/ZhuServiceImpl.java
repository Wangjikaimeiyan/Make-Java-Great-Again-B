package com.example.Service.Manage.impl;

import com.example.Pojo.Dish;
import com.example.Mapper.Manage.ZhuMapper;
import com.example.Service.Manage.ZhuService;
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
public class ZhuServiceImpl implements ZhuService {
    @Autowired
    private ZhuMapper zhuMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ChatClient chatClient;
    @Autowired
    private CleanCache cleanCache;
    // 查询全部主食
    @Override
    public List<Dish> searchAllDishes() {
        log.info("查询所有主食"+"ZhuServiceImpl");
        return zhuMapper.searchAllDishes();
    }

    @Override
    public void aiChat(String question, SseEmitter sseEmitter) {
        try {
            String text = "以上是所有主食,现在你是一位专业的主食AI助手，部署在餐厅点菜系统里，负责给顾客介绍主食。回答要求：1.口语化、亲切简洁、可爱俏皮 2.介绍名称、简单信息、推荐理由 3.简短精悍，抓住顾客心理 4.自带温度，触动人心，引人共情";
            List<Dish> dishes = zhuMapper.searchAllDishesforAI();
            String dishes_text = objectMapper.writeValueAsString(dishes);
            String endQuestion = dishes_text + "\n" + text +"\n"+ question;

            Flux<String> contentFlux = chatClient.prompt()
                    .user(endQuestion)
                    .stream()
                    .content();

            contentFlux.subscribe(
                    content -> {
                        try {
                            sseEmitter.send(content);
                        } catch (Exception e) {
                            log.warn("SSE send failed", e);
                            sseEmitter.completeWithError(e);
                        }
                    },
                    sseEmitter::completeWithError,
                    sseEmitter::complete
            );
        } catch (Exception e) {
            sseEmitter.completeWithError(e);
        }
    }

    // 查询单个菜品
    @Override
    public Dish searchDish(Integer id) {
        log.info("查询单个菜品"+"ZhuServiceImpl");
        return zhuMapper.searchDish(id);
    }

    // 新增
    @Override
    @Transactional(rollbackFor = {Exception.class})
    @CacheEvict(value = "Dish",allEntries = true)
    public void addDish(Dish dish) {
        log.info("新增菜品"+"ZhuServiceImpl");
        zhuMapper.addDish(dish);
        //        清空redis缓存
//        cleanCache.cleanCache("Dish::all");
        log.info("新增菜品成功"+"ZhuServiceImpl");
    }

    // 修改
    @Override
    @Transactional(rollbackFor = {Exception.class})
    @CacheEvict(value = "Dish",allEntries = true)
    public void updateDish(Dish dish) {
        log.info("修改菜品"+"ZhuServiceImpl");
        zhuMapper.updateDish(dish);
        //        清空redis缓存
//        cleanCache.cleanCache("Dish::all");
        log.info("修改菜品成功"+"ZhuServiceImpl");
    }

    // 删除
    @Override
    @Transactional(rollbackFor = {Exception.class})
    @CacheEvict(value = "Dish",allEntries = true)
    public void deleteDish(Integer id) {
        log.info("删除菜品"+"ZhuServiceImpl");
        zhuMapper.deleteDish(id);
        //        清空redis缓存
//        cleanCache.cleanCache("Dish::all");
        log.info("删除菜品成功"+"ZhuServiceImpl");
    }

    // 条件搜索
    @Override
    public List<Dish> searchDishes(String name, BigDecimal price) {
        log.info("条件搜索"+"ZhuServiceImpl");
        List<Dish> dishes = zhuMapper.searchDishes(name, price);
        log.info("条件搜索成功"+"ZhuServiceImpl");
        return dishes;
    }
}
