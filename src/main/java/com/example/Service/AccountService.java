package com.example.Service;

import com.example.Pojo.LoginInfo;

public interface AccountService {
    LoginInfo login(String username, String password);
}
