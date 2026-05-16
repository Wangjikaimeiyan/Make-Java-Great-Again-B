package com.example;

import jdk.jfr.Enabled;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
@SpringBootApplication
@ServletComponentScan
//配置绑定
@EnableConfigurationProperties
//开启缓存注解
@EnableCaching
//开启定时任务
@EnableScheduling
public class DiningManageApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiningManageApplication.class, args);
		System.out.println("启动成功");
	}

}
