package com.example.Controller;


import com.example.Service.XiangService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/Xiang")
@RestController
public class XiangController {
    @Autowired
    private XiangService xiangService;
}
