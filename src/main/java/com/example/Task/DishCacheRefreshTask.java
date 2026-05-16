package com.example.Task;

import com.example.Pojo.Dish;
import com.example.Service.WxApp.AlldishesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class DishCacheRefreshTask {

    @Autowired
    private AlldishesService alldishesService;

    /**
     * 每10秒强制刷新菜品缓存（测试用）
     * cron表达式: 秒 分 时 日 月 周
     * * /10 * * * * ? 表示每10秒执行一次
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    @CacheEvict(value = "Dish", key = "'all'")
    public void refreshDishCache() {
        log.info("====== 开始定时刷新菜品缓存 ======");
        try {
            // 重新查询数据库并缓存（@CacheEvict 已清除旧缓存，@Cacheable 会自动缓存新数据）
            List<Dish> dishes = alldishesService.searchAllDishes();
            log.info("从数据库查询到 {} 个菜品，已更新缓存", dishes.size());

            log.info("====== 菜品缓存刷新完成 ======");
        } catch (Exception e) {
            log.error("定时刷新菜品缓存失败", e);
        }
    }
}
