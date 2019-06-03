package com.bookkeeping.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.bookkeeping.mapper") // 扫描mapper接口所在的包
public class MybatisConfig {

}
