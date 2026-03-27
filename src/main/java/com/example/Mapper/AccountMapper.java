package com.example.Mapper;

import com.example.Pojo.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AccountMapper {
//    登录
    @Select("select id,account,password,name from accounts where account = #{account} and password = #{password}")
    Account login(String account, String password);
}
