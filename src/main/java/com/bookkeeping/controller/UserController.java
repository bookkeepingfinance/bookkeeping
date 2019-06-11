package com.bookkeeping.controller;


import com.bookkeeping.common.session.SessionService;
import com.bookkeeping.controller.response.Response;
import com.bookkeeping.entity.SessionData;
import com.bookkeeping.entity.User;
import com.bookkeeping.entity.UserSession;
import com.bookkeeping.entity.UserWechat;
import com.bookkeeping.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    @Autowired
    private SessionService sessionService;

    @Resource
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

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

        // 返回数据
        Response<Map> build = null;
        // 验证code是否正确


        User user = User.builder().mobile(mobile).build();
        // 判断手机号是否存在，user表中查询当前手机号
        Long userId = userService.mobileIsExit(mobile);


        HttpSession session = httpServletRequest.getSession();
        // 创建Session数据    sessionId  userId  sessionData
        SessionData sessionData = new SessionData();
        sessionData.setMobile(mobile);
        sessionData.setDeviceNo(deviceNo);

        UserSession userSession = new UserSession();
        userSession.setSessionId(session.getId());
        userSession.setUserId(userId);
        try {
            userSession.setSessionData(objectMapper.writeValueAsString(sessionData));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        if (userId == -1){
            // User表插入用户 唯一，插入操
            Boolean result  = userService.createMobileUser(user);
            userId = user.getId();
            userSession.setUserId(userId);
            if (!result){
                build = Response.<Map>builder().
                        flag("0").
                        message("登录失败").build();
                return ResponseEntity.ok(build);
            }
        }

        try {
            sessionService.save(userSession);
            session.setAttribute("user",userSession);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map responseData = new TreeMap<>();
        responseData.put("sessionId",session.getId());
        build = Response.<Map>builder().
                flag("1").
                message("登录成功").
                data(responseData).build();
        return ResponseEntity.ok(build);
    }

    /**
     * 微信登录
     *
     * @Author:wanghua
     * @Date:09:22 2019-05-31
     */
    @RequestMapping(value = "/loginByWechat",method = RequestMethod.POST)
    public ResponseEntity<Response<Map>> loginWithWechat(@RequestHeader(value = "deviceNo",required = false)String deviceNo,
                                                         String openId,
                                                         String name,
                                                         String headUrl,
                                                         Integer gender){

        // 返回数据
        Response<Map> build = null;
        // 判断用户是否存在
        Long userId = userService.wechatIsExit(openId);
        if (userId == -1) { // 用户不存在
            // 创建用户,拿到用户ID
            User user = User.builder().name(name).gender(gender).build();
            Boolean result = userService.createMobileUser(user);
            userId = user.getId();
            // 更新UserWechat表
            UserWechat userWechat = UserWechat.builder().
                    openId(openId).
                    name(name).
                    headUrl(headUrl).
                    gender(gender).
                    userId(userId).
                    build();
            Boolean createWechatResult = userService.createWechatUser(userWechat);
            if (!createWechatResult){
                build = Response.<Map>builder().
                        flag("0").
                        message("登录失败").build();
                return ResponseEntity.ok(build);
            }
        }

        // 更新session表
        HttpSession session = httpServletRequest.getSession();
        // 创建Session数据    sessionId  userId  sessionData
        SessionData sessionData = new SessionData();
        sessionData.setOpenId(openId);
        sessionData.setName(name);
        sessionData.setGender(gender);
        sessionData.setDeviceNo(deviceNo);
        UserSession userSession = new UserSession();
        userSession.setSessionId(session.getId());
        userSession.setUserId(userId);
        try {
            userSession.setSessionData(objectMapper.writeValueAsString(sessionData));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try {
            sessionService.save(userSession);
            session.setAttribute("user",userSession);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map responseData = new TreeMap<>();
        responseData.put("sessionId",session.getId());
        build = Response.<Map>builder().
                flag("1").
                message("登录成功").
                data(responseData).build();
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
