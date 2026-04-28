package com.example.dining_manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import java.util.concurrent.TimeUnit;

import static org.yaml.snakeyaml.nodes.Tag.STR;

@SpringBootTest
public class Test {
    //        System.out.println(STR."name:/{name}");   JDK21支持
    @Autowired
    private RedisTemplate redisTemplate;
//
//    @org.junit.jupiter.api.Test
//    public void testRedis() {
//        System.out.println(redisTemplate);
//// 操作string的对象
//        ValueOperations valueOperations = redisTemplate.opsForValue();
////       操作hash
//        HashOperations hashOperations = redisTemplate.opsForHash();
//    }

    @org.junit.jupiter.api.Test
    public void teststring () {
//        set,设置name 的值为张三，并设置过期时间为30秒
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("name","张三",30, TimeUnit.SECONDS);
//        get
        System.out.println(redisTemplate.opsForValue().get("name"));
    }
//    操作哈希数据
    @org.junit.jupiter.api.Test
    public void testHash() {
//        set
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put("user","name","张三");
        hashOperations.put("user","age","18");
        hashOperations.put("user","sex","男");
//        get
        String name = (String) hashOperations.get("user","name");
        System.out.println(name);
//        删除sex
        redisTemplate.opsForHash().delete("user","sex");
//        获取keys
        System.out.println(redisTemplate.opsForHash().keys("user"));
    }
//    操作列表
    @org.junit.jupiter.api.Test
    public void testList() {
        ListOperations listOperations = redisTemplate.opsForList();
//        插入 数据
        listOperations.leftPush("list","张三");
        listOperations.leftPushAll("list","张四","张五","张六");
//        range: 获取数据
        System.out.println(listOperations.range("list",0,-1));
//        rightpop
        System.out.println(listOperations.rightPop("list"));
//        llen
        System.out.println(listOperations.size("list"));
    }
//    集合操作
    @org.junit.jupiter.api.Test
    public void testSet() {
        SetOperations setOperations = redisTemplate.opsForSet();
        setOperations.add("set1","张三","张四","张5","张6");
        setOperations.add("set2","张三","张四","张7","张8");
//        查所有
        System.out.println("所有元素"+setOperations.members("set1"));
//        个数
        System.out.println("个数"+setOperations.size("set1"));
//        交集
        System.out.println("交集"+setOperations.intersect("set1","set2"));
//        并集
        System.out.println("并集"+setOperations.union("set1","set2"));
//        删除
        setOperations.remove("set1","张三");
        System.out.println("删除后set1"+setOperations.members("set1"));
    }
//    有序集合
    @org.junit.jupiter.api.Test
    public void testZSet() {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add("zset","张三",10);
        zSetOperations.add("zset","张四",15);
//        range：获取数据
        System.out.println(zSetOperations.range("zset",0,-1));
//        zincrBy：修改分数
        zSetOperations.incrementScore("zset","张三",10);
        System.out.println(zSetOperations.range("zset",0,-1));
//        zremove：删除
        zSetOperations.remove("zset","张三");
        System.out.println(zSetOperations.range("zset",0,-1));
    }
//    通用命令
    @org.junit.jupiter.api.Test
    public void testCommon() {
//        keys
        System.out.println(redisTemplate.keys("*"));
//        exist
        System.out.println(redisTemplate.hasKey("nam1"));
//        type
        System.out.println(redisTemplate.type("nam1"));
//        del
        redisTemplate.delete("nam1");
    }
}
