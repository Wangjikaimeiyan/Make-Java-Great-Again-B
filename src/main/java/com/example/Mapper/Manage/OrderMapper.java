package com.example.Mapper.Manage;

import com.example.Pojo.DishesSales;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
//    查询所有销量，只查询前10条
    List<DishesSales> allSales();
}
