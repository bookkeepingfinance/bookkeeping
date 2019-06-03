package com.bookkeeping.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* 用户微信表
*
* @Author:wanghua
* @Date:09:47 2019-05-31
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserWechat {
    private Long id;            // 主键
    private String openId;      // openID
    private Long userId;        // 用户ID
    private String name;        // 用户名
    private String headUrl;     // 微信头像URL
    private Integer gender;     // 用户性别 0 未知，1 男，2 女


}
