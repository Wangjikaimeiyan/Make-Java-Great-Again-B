package com.example.Controller;

import com.example.Pojo.Dish;
import com.example.Pojo.Result;
import com.example.Service.ChuanService;
import com.example.Utils.AliyunOSSOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

//川菜控制
@Slf4j
@RequestMapping("/Chuan")
@RestController
public class ChuanController {
    @Autowired
    private ChuanService chuanService;
    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

//    查询所有川菜
    @GetMapping("/dishes")
    public Result searchAllDishes() {
        log.info("查询所有川菜");
        List<Dish> dishes = chuanService.searchAllDishes();
        return Result.success(dishes);
    }
//     Ai分析菜品
    @PostMapping(value = "/ai/chat",produces = "text/event-stream;charset=UTF-8")
    public SseEmitter aiChat(String question) {
        log.info("Ai分析菜品");
        SseEmitter sseEmitter = new SseEmitter(60000L);
        chuanService.aiChat(question,sseEmitter);
        return sseEmitter;
    }
//    查询单个菜品
    @GetMapping("/dish/{id}")
    public Result searchDish(@PathVariable Integer id) {
        log.info("查询单个菜品");
        Dish dish = chuanService.searchDish(id);
        return Result.success(dish);
    }
//    新增菜品
    @PostMapping("/dish")
    public Result addDish(
            @RequestParam("name") String name,
            @RequestParam("price")BigDecimal price,
            @RequestParam("detail") String detail,
            @RequestParam("image") MultipartFile image,
            @RequestParam("category") Integer category
            )throws  Exception {
        log.info("新增菜品");
//        1,先将图片存入阿里云
        String imageUrl = aliyunOSSOperator.upload(image.getBytes(), image.getOriginalFilename());
//        2,databae存入 数据
        Dish dish = new Dish();
        dish.setName(name);
        dish.setPrice(price);
        dish.setDetail(detail);
        dish.setImage(imageUrl);
        dish.setCategory(category);
        dish.setCreateTime(LocalDateTime.now());
        dish.setUpdateTime(LocalDateTime.now());
        try {
            chuanService.addDish(dish);
        } catch (Exception e) {
//            事务回滚，删除图片
            log.error("添加菜品失败，回滚事务");
            aliyunOSSOperator.delete(imageUrl);
            throw new RuntimeException("添加菜品失败");
        }
        return Result.success("添加成功");
    }
//    修改菜品
    @PostMapping("/Updatadish")
    public Result updateDish(
            @RequestParam("id") Integer id,
            @RequestParam("name") String name,
            @RequestParam("price")BigDecimal price,
            @RequestParam("detail") String detail,
            @RequestParam(value = "image",required = false) MultipartFile image,
            @RequestParam("category") Integer category
    )throws Exception{
        String A = null;
        String oldImageUrl = null;
        if(image != null){
            oldImageUrl = chuanService.searchDish(id).getImage();/* 获取旧图片地址 */
            A = aliyunOSSOperator.upload(image.getBytes(), image.getOriginalFilename());
            System.out.println("A:"+A);
        }
        log.info("修改菜品");
        Dish dish = new Dish();
        dish.setImage(A);/* 设置图片地址 */
        dish.setId(id);
        dish.setName(name);
        dish.setPrice(price);
        dish.setDetail(detail);
        dish.setCategory(category);
        dish.setUpdateTime(LocalDateTime.now());
        try {
            chuanService.updateDish(dish);
        } catch (Exception e) {
            log.error("修改菜品失败，回滚事务");
            if (A != null) {
                aliyunOSSOperator.delete(A);
            }
            throw new RuntimeException("修改失败");
        }
        try {
            aliyunOSSOperator.delete(oldImageUrl);
        } catch (Exception e) {
            throw new RuntimeException("删除图片出现问题");
        }
        return Result.success("修改成功");
    }
//    删除菜品
    @DeleteMapping("/dish/{id}")
    public Result deleteDish(@PathVariable Integer id) throws Exception {
        log.info("删除菜品");
//        首先获取图片地址
        String imageUrl = chuanService.searchDish(id).getImage();
        try {
            chuanService.deleteDish(id);
        } catch (Exception e) {
            log.error("删除菜品失败，回滚事务");
            throw new RuntimeException("删除菜品失败");
        }
        try {
            if (imageUrl != null) {
                aliyunOSSOperator.delete(imageUrl);
            }
        } catch (Exception e) {
//            拓展接口，出现问题首先储存图片地址
        }
        return Result.success("删除成功");
    }
//    条件搜索
    @GetMapping("/dishes/filter")
    public Result searchDishes(
            @RequestParam(value = "name",required = false) String name,
            //        价格是最高价往下帅选
            @RequestParam(value = "price",required = false) BigDecimal price
    ) {
        List<Dish> dishes = chuanService.searchDishes(name,price);
        log.info("条件搜索");
        return Result.success(dishes);
    }
}

























