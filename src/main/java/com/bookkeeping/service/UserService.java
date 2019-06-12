package com.bookkeeping.service;

import com.bookkeeping.entity.User;
import com.bookkeeping.entity.UserWechat;
import com.bookkeeping.mapper.UserMapper;
import com.bookkeeping.mapper.UserWechatMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserWechatMapper userWechatMapper;

    /**
    * 手机号是否存在
    *
    * @Author:wanghua
    * @Date:11:26 2019-06-03
    */
    public Long mobileIsExit(String mobile) {
         return userMapper.mobileIsExit(mobile);
    }

    /**
    * 微信账号是否存在
    *
    * @Author:wanghua
    * @Date:11:58 2019-06-03
    */
    public Long wechatIsExit(String openId) {
        return userWechatMapper.wechatIsExit(openId);
    }

    /**
     * 创建手机用户
     *
     * @Author:wanghua
     * @Date:11:24 2019-06-03
     */
    public Integer createMobileUser(User user) {
        return userMapper.createMobileUser(user);
    }

    /**
     * 创建微信用户
     *
     * @Author:wanghua
     * @Date:11:25 2019-06-03
     */
    public Integer createWechatUser(UserWechat userWechat) {
        return userWechatMapper.createWechatUser(userWechat);
    }

    /**
     * 微信绑定手机号
     *
     * @Author:wanghua
     * @Date:11:25 2019-06-03
     */
    public Integer bindMobile(String mobile) {
        return userWechatMapper.bindMobile(mobile);
    }

    /**
    * 查找用户WeChat
    *
    * @Author:wanghua
    * @Date:10:14 2019-06-12
    */
    public UserWechat findUserWechat(Long userId){
        return userWechatMapper.findUserWechat(userId);
    }



    /**
    * 更新user
    *
    * @Author:wanghua
    * @Date:10:18 2019-06-12
    */
    public Integer updateUser(User user){
        return userMapper.updateUser(user);
    }


    /**
     * 修改微信表userId
     *
     * @Author:wanghua
     * @Date:11:22 2019-06-12
     */
    public Integer updateWechatUserId(Long oldUserId,Long newUserId){
        return userWechatMapper.updateWechatUserId(oldUserId,newUserId);
    }
}
