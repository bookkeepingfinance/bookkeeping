package com.bookkeeping.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* 用户表
*
* @Author:wanghua
* @Date:09:44 2019-05-31
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;        // 主键
    private String name;    // 用户名
    private Integer gender; // 用户性别 0 未知，1 男，2 女
    private String mobile;  // 手机号
    private String password;// 密码
}
