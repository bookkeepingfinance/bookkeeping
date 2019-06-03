package com.bookkeeping.entity;

import lombok.Builder;
import lombok.Data;

/**
* 用户设备表
*
* @Author:wanghua
* @Date:09:45 2019-05-31
*/
@Data
@Builder
public class UserDevice {

    private Long id;            // 主键
    private String deviceNo;    // 设备号
    private String clientType;  // 客户端类型：0 未知，1 iOS，2 Android
    private Long userId;        // 用户id


}
