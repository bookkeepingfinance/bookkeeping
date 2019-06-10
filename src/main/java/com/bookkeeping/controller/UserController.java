package com.bookkeeping.controller;


import com.bookkeeping.controller.response.Response;
import com.bookkeeping.entity.User;
import com.bookkeeping.entity.UserWechat;
import com.bookkeeping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.TreeMap;


/**
* 用户登录
*
* @Author:wanghua
* @Date:09:34 2019-05-31
*/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Resource
    private UserService userService;

//    @RequestHeader("deviceNo")String deviceNo,@RequestParam(name = "mobile") String mobile,@PathVariable(name = "name") String na

    /**
    * 手机号登录
    * param:mobile、code
    * @Author:wanghua
    * @Date:09:22 2019-05-31
    */
    @RequestMapping(value = "/loginByMobile",method = RequestMethod.POST)
    public ResponseEntity<Response<Map>> loginWithMobile(@RequestHeader(value = "deviceNo",required = false)String deviceNo,
                                                         String mobile,
                                                         @RequestParam(value = "code",required = false)String code){
        // 验证code是否正确


        User user = User.builder().mobile(mobile).build();
        // 判断手机号是否存在，user表中查询当前手机号
        Long userId = userService.mobileIsExit(mobile);
        if (userId == -1){  // 用户不存在
            // User表插入用户 唯一，插入操
            Boolean result  = userService.createMobileUser(user);
            Long propertyKey = user.getId();
            if (result){
                // 创建成功
                System.out.println("用户添加成功");
                // session表更新
            }else {
                // 创建失败
                System.out.println("用户添加失败");
            }

        }else {
            // 如果存在,更新session表和sessionData

        }

        Map result = new TreeMap<>();
        result.put("userId",user.getId());

        Response<Map> build = Response.<Map>builder().
                flag("1").
                message("成功").
                data(result).build();
        return ResponseEntity.ok(build);
    }

    /**
     * 微信登录
     *
     * @Author:wanghua
     * @Date:09:22 2019-05-31
     */
    @RequestMapping(value = "/loginByWechat",method = RequestMethod.POST)
    public ResponseEntity<Response<Map>> loginWithWechat(String openId,
                                                         String name,
                                                         String headUrl,
                                                         Integer gender){

        // 判断用户是否存在
        Long id = userService.wechatIsExit(openId);
        if (id == -1) { // 用户不存在
            // 创建用户,拿到用户ID
            User user = User.builder().name(name).gender(gender).build();
            Boolean result = userService.createMobileUser(user);
            // 更新WeChat表
            UserWechat userWechat = UserWechat.builder().
                    userId(user.getId()).
                    openId(openId).
                    name(name).
                    headUrl(headUrl).
                    gender(gender).build();
            Boolean createWechatResult = userService.createWechatUser(userWechat);
            if (createWechatResult){
                // 微信表更新成功

            }else {

            }
        }else {
            // 更新用户
        }
        // 返回登录成功和session
        Map result = new TreeMap<>();
        result.put("sessionId","123456789");
        Response<Map> build = Response.<Map>builder().
                flag("1").
                message("成功").
                data(result).
                build();
        return ResponseEntity.ok(build);
    }



    /**
    * 退出登录
    *
    * @Author:wanghua
    * @Date:14:30 2019-05-31
    */
    @RequestMapping(value = "/loginOut",method = RequestMethod.POST)
    public ResponseEntity<Response<Map>> loginOut(@RequestHeader("sessionId") String sessionId){
        Response<Map> build = Response.<Map>builder().
                flag("1").
                message("成功").
                data(new TreeMap()).build();
        return ResponseEntity.ok(build);
    }

}
