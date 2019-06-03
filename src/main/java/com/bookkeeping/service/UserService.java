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
        Long result = userMapper.mobileIsExit(mobile);
         return result == null?-1:result;
    }

    /**
    * 微信账号是否存在
    *
    * @Author:wanghua
    * @Date:11:58 2019-06-03
    */
    public Long wechatIsExit(String openId) {
        Long result = userWechatMapper.wechatIsExit(openId);
        return result == null?-1:result;
    }

    /**
     * 创建手机用户
     *
     * @Author:wanghua
     * @Date:11:24 2019-06-03
     */
    public Boolean createMobileUser(User user) {
        return userMapper.createMobileUser(user);
    }

    /**
     * 创建微信用户
     *
     * @Author:wanghua
     * @Date:11:25 2019-06-03
     */
    public Boolean createWechatUser(UserWechat userWechat) {
        return userWechatMapper.createWechatUser(userWechat);
    }

    /**
     * 微信绑定手机号
     *
     * @Author:wanghua
     * @Date:11:25 2019-06-03
     */
    public Boolean bindMobile(String mobile) {
        return userWechatMapper.bindMobile(mobile);
    }

}
