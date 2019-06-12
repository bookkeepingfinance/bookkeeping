package com.bookkeeping.service;

import com.bookkeeping.mapper.FinanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinanceService {

    @Autowired
    private FinanceMapper financeMapper;

    /**
     * 批量更新数据对应的userID
     *
     * @Author:wanghua
     * @Date:11:07 2019-06-12
     */
    public Integer mergeData(Long oldUserId,Long newUserId){
        return financeMapper.mergeData(oldUserId,newUserId);
    }

}
