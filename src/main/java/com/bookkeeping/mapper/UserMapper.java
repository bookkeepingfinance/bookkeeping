package com.bookkeeping.mapper;


import com.bookkeeping.entity.User;
import com.bookkeeping.entity.UserWechat;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    /**
    * 判断手机号是否存在，如果存在返回对应的userId,如果不存在返回-1
    *
    * @Author:wanghua
    * @Date:14:42 2019-05-31
    */
    Long mobileIsExit(String mobile);

    /**
    * 创建手机用户
    *
    * @Author:wanghua
    * @Date:11:24 2019-06-03
    */
    Integer createMobileUser(User user);



    /**
    * 更新user
    *
    * @Author:wanghua
    * @Date:10:17 2019-06-12
    */
    Integer updateUser(User user);

}
