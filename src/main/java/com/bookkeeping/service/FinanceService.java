package com.bookkeeping.service;

import com.bookkeeping.entity.Finance;
import com.bookkeeping.mapper.FinanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinanceService {

    @Autowired
    private FinanceMapper financeMapper;


    public Integer mergeData(Long oldUserId,Long newUserId){
        return financeMapper.mergeData(oldUserId,newUserId);
    }


    public Integer saveData(Finance finance) {
        return financeMapper.saveData(finance);
    }


    public Integer updateData(Finance finance) {
        return financeMapper.updateData(finance);
    }

    public Integer deleteData(Finance finance) {
        return financeMapper.deleteData(finance);
    }


    public List<Finance> selectData(Long userId) {
        return financeMapper.selectData(userId);
    }

}
