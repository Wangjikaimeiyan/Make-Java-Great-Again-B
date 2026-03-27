package com.example.Service.impl;

import com.example.Mapper.XiangMapper;
import com.example.Service.XiangService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class XiangServiceImpl implements XiangService {
    @Autowired
    private XiangMapper xiangMapper;
}
