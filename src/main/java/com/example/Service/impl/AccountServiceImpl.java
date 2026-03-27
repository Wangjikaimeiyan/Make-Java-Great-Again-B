package com.example.Service.impl;
import com.example.Mapper.AccountMapper;
import com.example.Pojo.Account;
import com.example.Pojo.LoginInfo;
import com.example.Service.AccountService;
import com.example.Utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public LoginInfo login(String username, String password) {
//        查询账户密码
        Account account = accountMapper.login(username,password);
//        调用jwt
        if (account != null){
            Map<String,Object> dataMap = new HashMap<>();
            dataMap.put("id",account.getId());
            dataMap.put("username",account.getAccount());
            dataMap.put("name",account.getName());

            String token = JwtUtils.generateJwt(dataMap);
            return new LoginInfo(account.getId(),account.getAccount(),account.getName(),token);
        }
        return null;
    }
}


















