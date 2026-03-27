package com.example.Controller;

import com.example.Pojo.Account;
import com.example.Pojo.LoginInfo;
import com.example.Pojo.Result;
import com.example.Service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginController {
    @Autowired
    private AccountService accountService;
//      请求登录页面
    @PostMapping("/login")
    public Result login(@RequestBody Account account) {
        LoginInfo loginInfo = accountService.login(account.getAccount(),account.getPassword());
        if (loginInfo != null) {
            return Result.success(loginInfo);
        }else {
            return Result.error("用户名或密码错误");
        }
    }
}
