package com.bookkeeping.entity;


import lombok.Builder;
import lombok.Data;

/**
* 用户session表
*
* @Author:wanghua
* @Date:13:54 2019-05-31
*/
@Data
@Builder
public class UserSession {
    private Long id;            // 主键
    private String sessionId;   // session_id
    private Long userId;        // 用户ID
    private String  sessionData;// json格式的session数据

}
