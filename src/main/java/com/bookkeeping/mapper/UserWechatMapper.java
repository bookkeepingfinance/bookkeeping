package com.bookkeeping.mapper;


import com.bookkeeping.entity.UserWechat;
import org.springframework.stereotype.Repository;

@Repository
public interface UserWechatMapper {

    /**
     * 判断微信账号是否存在，如果存在返回对应的userId,如果不存在返回-1
     *
     * @Author:wanghua
     * @Date:14:42 2019-05-31
     */
    Long wechatIsExit(String openId);


    /**
     * 创建微信用户
     *
     * @Author:wanghua
     * @Date:11:25 2019-06-03
     */
    Integer createWechatUser(UserWechat userWechat);

    /**
    * 修改userId
    *
    * @Author:wanghua
    * @Date:11:22 2019-06-12
    */
    Integer updateWechatUserId(Long oldUserId,Long newUserId);

    /**
    * 查找用户微信
    *
    * @Author:wanghua
    * @Date:10:10 2019-06-12
    */
    UserWechat findUserWechat(Long userId);


    /**
     * 微信绑定手机号
     *
     * @Author:wanghua
     * @Date:11:25 2019-06-03
     */
    Integer bindMobile(String mobile);

}
