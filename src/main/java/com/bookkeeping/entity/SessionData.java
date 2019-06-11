package com.bookkeeping.entity;

import lombok.Builder;
import lombok.Data;

@Data
//@Builder
public class SessionData {
    private String openId;
    private String name;
    private Integer gender;
    private String mobile;
    private String deviceNo;
}
