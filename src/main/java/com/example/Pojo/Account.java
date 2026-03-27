package com.example.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Account {
    private int id;
    private String account;
    private String name;
    private String password;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
