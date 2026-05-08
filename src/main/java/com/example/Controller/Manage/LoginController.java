package com.example.Controller.Manage;

import com.example.Pojo.Account;
import com.example.Pojo.LoginInfo;
import com.example.Pojo.Result;
import com.example.Service.Manage.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "登录接口")
public class LoginController {
    @Autowired
    private AccountService accountService;
//      请求登录页面
    @PostMapping("/login")
    @Operation(summary = "登录")
    public Result login(@RequestBody Account account) {
        LoginInfo loginInfo = accountService.login(account.getAccount(),account.getPassword());
        if (loginInfo != null) {
            return Result.success(loginInfo);
        }else {
            return Result.error("用户名或密码错误");
        }
    }
}
