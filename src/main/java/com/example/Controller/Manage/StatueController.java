package com.example.Controller.Manage;

import com.example.Pojo.Result;
import com.example.Service.StatueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/Statue")
//@RestController("StatueController")自定义bean名称
@RestController
@Tag(name = "营业状态")
//营业状态
public class StatueController {
    @Autowired
    private StatueService statueService;

//    修改营业状态
    @GetMapping("/update")
    @Operation(summary = "修改营业状态")
    public Result updateStatue(@RequestParam Integer statue) {
        // statue 接收前端 0 / 1
        // 核心：调用service更新数据库状态
        log.info("修改营业状态：{}", statue);
        statueService.updateStatue(statue);
        log.info("✅ 营业状态修改成功");
        return Result.success("修改成功");
    }
    // 查询店铺营业状态
    @GetMapping("/query")
    @Operation(summary = "查询营业状态")
    public Result queryShopStatus() {
        // 从数据库查当前 statue 0/1
        log.info("查询营业状态");
        Integer statue = statueService.getShopStatue();
        log.info("营业状态：{}", statue);
        return Result.success(statue);
    }

}
