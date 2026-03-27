package com.example.Service.impl;

import com.example.Mapper.ZhuMapper;
import com.example.Service.ZhuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ZhuServiceImpl implements ZhuService {
    @Autowired
    private ZhuMapper zhuMapper;
}
