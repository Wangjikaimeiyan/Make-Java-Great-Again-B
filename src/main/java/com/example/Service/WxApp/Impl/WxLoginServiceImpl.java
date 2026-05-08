package com.example.Service.WxApp.Impl;

import com.example.Mapper.WxApp.WxUserMapper;
import com.example.Pojo.WxUser;
import com.example.Service.WxApp.WxLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WxLoginServiceImpl implements WxLoginService {
    @Autowired
    private WxUserMapper wxUserMapper;

    @Override
    public void saveWxUser(WxUser wxUser) {
//        调用Mapper层
        WxUser tempUser = wxUserMapper.selectByOpenid(wxUser.getOpenid());
        if (tempUser != null) {
            //  存在：执行更新
            // 把数据库里的 id 回填到传入的对象中
            wxUser.setId(tempUser.getId());
            wxUserMapper.updateWxUser(wxUser);
        } else {
            //  insert 后，MyBatis 会自动把自增 id 回填到 wxUser 对象中
            wxUser.setStatus(0);
            wxUserMapper.insertWxUser(wxUser);
        }
    }
}
