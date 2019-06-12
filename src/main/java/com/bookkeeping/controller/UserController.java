package com.bookkeeping.controller;


import com.bookkeeping.common.session.SessionService;
import com.bookkeeping.controller.response.Response;
import com.bookkeeping.entity.SessionData;
import com.bookkeeping.entity.User;
import com.bookkeeping.entity.UserSession;
import com.bookkeeping.entity.UserWechat;
import com.bookkeeping.service.FinanceService;
import com.bookkeeping.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier(value = "sessionServiceImpl")
    private SessionService sessionService;

    @Resource
    private UserService userService;

    @Autowired
    private FinanceService financeService;

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


        if (null == userId){
            // User表插入用户 唯一，插入操
            userService.createMobileUser(user);
            userId = user.getId();
            userSession.setUserId(userId);
        }

        try {
            sessionService.save(userSession);
            session.setAttribute("user",userSession);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map responseData = new TreeMap<>();
        responseData.put("sessionId",session.getId());
        return ResponseEntity.ok(initResponse("1","登录成功",responseData));
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

        // 判断用户是否存在
        Long userId = userService.wechatIsExit(openId);
        if (null == userId) { // 用户不存在
            // 创建用户,拿到用户ID
            User user = User.builder().name(name).gender(gender).build();
            userService.createMobileUser(user);
            userId = user.getId();
            // 更新UserWechat表
            UserWechat userWechat = UserWechat.builder().
                    openId(openId).
                    name(name).
                    headUrl(headUrl).
                    gender(gender).
                    userId(userId).
                    build();
            userService.createWechatUser(userWechat);
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
        return ResponseEntity.ok(initResponse("1","登录成功",responseData));
    }


    public Response<Map> initResponse(String flag,String message,Map responseData) {
        return Response.<Map>builder().flag(flag).message(message).data(responseData).build();
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

    /**
     * 绑定手机号
     *
     * @Author:wanghua
     * @Date:14:30 2019-05-31
     */
    @RequestMapping(value = "/bindMobile",method = RequestMethod.POST)
    public ResponseEntity<Response<Map>> bindMobile(@RequestHeader("sessionId") String sessionId,
                                                    @RequestHeader(value = "deviceNo",required = false)String deviceNo,
                                                    String mobile,
                                                    @RequestParam(value = "code",required = false)String code){

        /**
        * 微信登录状态
         * 1、判断验证码是否正确，正确继续，不正确返回验证码失败
         * 2、判断手机号是否存在，如果不存在，更新user表，更新session表，返回session
         * 3、如果手机号存在
         * 手机号没有绑定微信，绑定手机号，更新user表，更新微信表，更新session表，更新数据表
         * 手机号已经绑定微信，如果已经绑定，返回该手机号已经绑定
        */

        // 1、校验验证码


        // 2、判断手机号是否存在，通过手机号查询user表
        Long userId = userService.mobileIsExit(mobile);
        // 手机号不存在，通过sessionID获取userID，更新user表，更新session表，返回sessionID
        if (null == userId){
            HttpSession session = httpServletRequest.getSession();
            userId = sessionService.findUserId(session.getId());
            // 通过userID查询微信表中用户数据
            UserWechat userWechat = userService.findUserWechat(userId);
            // 创建user对象
            User user = User.builder().
                    id(userId).
                    gender(userWechat.getGender()).
                    mobile(mobile).
                    name(userWechat.getName()).build();
            // 更新user表
            userService.updateUser(user);
            // 更新session表
            SessionData sessionData = new SessionData();
            sessionData.setOpenId(userWechat.getOpenId());
            sessionData.setName(userWechat.getName());
            sessionData.setGender(userWechat.getGender());
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
            responseData.put("reload","0");
            return ResponseEntity.ok(initResponse("1","绑定成功",responseData));
        }else {
            // 3、通过手机号对应的userID查找WeChat表，查看是否有对应的openID
            UserWechat userWechat = userService.findUserWechat(userId);
            if (null != userWechat){
                // 手机号存在已经绑定微信，告知用户此手机号已经绑定其他微信，不可再次绑定
                Map responseData = new TreeMap<>();
                return ResponseEntity.ok(initResponse("0","该手机号已经绑定其他微信，不可再次绑定",responseData));
            }else {
                // 手机号存在没有绑定微信，将微信数据同步到手机号对应的userID的，包括user表、session表、数据表，同步完成后，将微信对应的userID删除
                // 通过session对应的userID获取WeChat表中用户数据，同步到手机号对应的userID
                HttpSession session = httpServletRequest.getSession();
                Long  wUserId = sessionService.findUserId(sessionId);

                // 通过userID查询微信表中用户数据
                userWechat = userService.findUserWechat(wUserId);
                // 创建user对象
                User user = User.builder().
                        id(userId).
                        gender(userWechat.getGender()).
                        mobile(mobile).
                        name(userWechat.getName()).build();
                // 更新user表
                userService.updateUser(user);

                // 通过session对应的userID获取Session表中用户数据，同步到手机号对应的userID
                // 更新session表
                SessionData sessionData = new SessionData();
                sessionData.setOpenId(userWechat.getOpenId());
                sessionData.setName(userWechat.getName());
                sessionData.setGender(userWechat.getGender());
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

                // 通过session对应的userID获取finance表中用户数据，同步到手机号对应的userID
                financeService.mergeData(wUserId,userId);
                // 修改微信表中的userID
                userService.updateWechatUserId(wUserId,userId);
                // 删除User表中微信对应的userID，session表

                Map responseData = new TreeMap<>();
                responseData.put("sessionId",session.getId());
                responseData.put("reload","1");
                return ResponseEntity.ok(initResponse("1","绑定成功",responseData));
            }
        }

    }



}
