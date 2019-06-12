package com.bookkeeping.mapper;

import org.springframework.stereotype.Repository;

@Repository
public interface FinanceMapper {

    /**
    * 批量更新数据对应的userID
    *
    * @Author:wanghua
    * @Date:11:07 2019-06-12
    */
    Integer mergeData(Long oldUserId,Long newUserId);


}
