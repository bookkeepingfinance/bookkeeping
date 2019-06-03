package com.bookkeeping.entity;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
* 财务表
*
* @Author:wanghua
* @Date:13:55 2019-05-31
*/
@Data
@Builder
public class Finance {

    private Long id;                // 主键
    private Long categoryChildId;   // 二级类别ID
    private Long userId;            // 用户ID
    private BigDecimal money;       // 金额
    private String remark;          // 记账备注
    private LocalDateTime date;     // 收入或支出产生时间
    private String data;            // 录入的原始文本


}
