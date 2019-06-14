package com.bookkeeping.mapper;

import com.bookkeeping.entity.Finance;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinanceMapper {

    /**
    * 批量更新数据对应的userID
    *
    * @Author:wanghua
    * @Date:11:07 2019-06-12
    */
    Integer mergeData(Long oldUserId,Long newUserId);


    /**
     * 插入数据
     * @params
     * @return
     * @date 2019-06-13
     */
    Integer saveData(Finance finance);


    /**
     * 修改数据
     * @params
     * @return
     * @date 2019-06-13
     */
    Integer updateData(Finance finance);


    /**
     * 删除数据
     * @params
     * @return
     * @date 2019-06-13
     */
    Integer deleteData(Finance finance);


    /**
     * 查询数据
     * @params
     * @return
     * @date 2019-06-13
     */
    List<Finance> selectData(Long userId);
}
