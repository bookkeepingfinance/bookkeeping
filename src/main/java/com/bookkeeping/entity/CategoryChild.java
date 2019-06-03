package com.bookkeeping.entity;


import lombok.Builder;
import lombok.Data;


/**
* 二级分类表
*
* @Author:wanghua
* @Date:13:55 2019-05-31
*/
@Data
@Builder
public class CategoryChild {
    private Long id;            // 主键
    private Long parentId;      // 父类别id
    private String name;        // 类别名称
    private Integer type;       // 收支类别：0 未知，1 支出，2 收入
    private String tips;        // 记账录入提示,逗号分隔


}
