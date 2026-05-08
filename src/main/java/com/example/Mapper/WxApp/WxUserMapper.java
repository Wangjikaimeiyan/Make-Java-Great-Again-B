package com.example.Mapper.WxApp;

import com.example.Pojo.WxUser;
import com.example.annotation.AutoFill;
import com.example.enumrtation.OperationType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WxUserMapper {
    // 根据 openid 查询用户
    WxUser selectByOpenid(String openid);

    // 新增用户（id 自增，不用传）
    @AutoFill(OperationType.INSERT)
    int insertWxUser(WxUser wxUser);

    // 更新用户（根据 id 更新）
    @AutoFill(OperationType.UPDATE)
    int updateWxUser(WxUser wxUser);
}
