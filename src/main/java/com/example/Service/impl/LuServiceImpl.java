package com.example.Service.impl;

import com.example.Mapper.LuMapper;
import com.example.Service.LuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LuServiceImpl implements LuService {
    @Autowired
    private LuMapper luMapper;
}
