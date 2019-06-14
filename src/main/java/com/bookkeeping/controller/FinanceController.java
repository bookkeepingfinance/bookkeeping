package com.bookkeeping.controller;

import com.bookkeeping.common.session.SessionService;
import com.bookkeeping.controller.response.Response;
import com.bookkeeping.entity.Finance;
import com.bookkeeping.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("/finance")
public class FinanceController {

    @Autowired
    private FinanceService financeService;

    private SessionService sessionService;
    /**
     * 记账
     * */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResponseEntity<Response<Map>> add(@RequestHeader(value = "sessionId") String sessionId,
                                                     Long categoryChildId,
                                                     Double money,
                                                     String remark,
                                                     String date,
                                                     String data) {

        /**
         *
         * 通过sessionID获取userID
         * 构建finance对象
         * 执行插入操作
         */
        Long userId = sessionService.findUserId(sessionId);

        Finance finance = Finance.builder().
                userId(userId).
                categoryChildId(categoryChildId).
                money(BigDecimal.valueOf(money)).
                remark(remark).
                data(data).
                date(LocalDateTime.parse(date)).build();
        financeService.saveData(finance);

        Map responseData = new TreeMap<>();
        return ResponseEntity.ok(Response.<Map>builder().flag("1").message("记账成功").data(responseData).build());

    }


    /**
     * 编辑
     * */
    @RequestMapping(value = "/reset",method = RequestMethod.POST)
    public ResponseEntity<Response<Map>> reset(@RequestHeader(value = "sessionId") String sessionId,
                                                     Long id,
                                                     Long categoryChildId,
                                                     Double money,
                                                     String remark,
                                                     String date,
                                                     String data) {

        /**
         *
         * 通过sessionID获取userID
         * 构建finance对象
         * 执行插入操作
         */
        Long userId = sessionService.findUserId(sessionId);

        Finance finance = Finance.builder().
                id(id).
                userId(userId).
                categoryChildId(categoryChildId).
                money(BigDecimal.valueOf(money)).
                remark(remark).
                data(data).
                date(LocalDateTime.parse(date)).build();
        financeService.updateData(finance);

        Map responseData = new TreeMap<>();
        return ResponseEntity.ok(Response.<Map>builder().flag("1").message("修改成功").data(responseData).build());

    }

    /**
     * 删除
     * */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public ResponseEntity<Response<Map>> delete(@RequestHeader(value = "sessionId") String sessionId,
                                               Long id) {

        /**
         *
         * 通过sessionID获取userID
         * 构建finance对象
         * 执行插入操作
         */
        Long userId = sessionService.findUserId(sessionId);

        Finance finance = Finance.builder().
                id(id).
                userId(userId).
                build();
        financeService.deleteData(finance);

        Map responseData = new TreeMap<>();
        return ResponseEntity.ok(Response.<Map>builder().flag("1").message("删除成功").data(responseData).build());

    }



    /**
     * 查询账单
     * 参数：时间周期，选中时间段
     * 返回值：支出、收入、结余、list
     * */
    @RequestMapping(value = "/findBill",method = RequestMethod.POST)
    public ResponseEntity<Response<Map>> findBill(@RequestHeader(value = "sessionId") String sessionId,
                                                  Integer timeType,
                                                  String period) {



        Map responseData = new TreeMap<>();
        responseData.put("income","10000");
        responseData.put("expenses","100000");
        responseData.put("balance","12343");
        responseData.put("list",new ArrayList<String>());
        return ResponseEntity.ok(Response.<Map>builder().flag("1").message("删除成功").data(responseData).build());

    }



    /**
     * 查询统计图表数据
     * 参数：时间周期，选中时间段，收支type
     * 返回值：收入、支出、结余、对应financeType平均、list
     * */
    @RequestMapping(value = "/findStatisticalData",method = RequestMethod.POST)
    public ResponseEntity<Response<Map>> findStatisticalData(@RequestHeader(value = "sessionId") String sessionId,
                                                             Integer timeType,
                                                             String period,
                                                             Integer financeType) {



        Map responseData = new TreeMap<>();
        responseData.put("income","10000");
        responseData.put("expenses","100000");

        responseData.put("average","10000");
        responseData.put("balance","12343");
        responseData.put("list",new ArrayList<String>());
        return ResponseEntity.ok(Response.<Map>builder().flag("1").message("删除成功").data(responseData).build());

    }



    /**
     * 查询分类图表数据
     * 参数：时间周期，选中时间段，收支type
     * 返回值：累计支出、累计收入、收入list、支出list
     * */
    @RequestMapping(value = "/findCategoryData",method = RequestMethod.POST)
    public ResponseEntity<Response<Map>> findCategoryData(@RequestHeader(value = "sessionId") String sessionId,
                                                             Integer timeType,
                                                             String period,
                                                             Integer financeType) {



        Map responseData = new TreeMap<>();
        responseData.put("income","10000");
        responseData.put("expenses","100000");
        responseData.put("list",new ArrayList<String>());
        return ResponseEntity.ok(Response.<Map>builder().flag("1").message("删除成功").data(responseData).build());

    }



    /**
     * 查询趋势图表数据
     * 参数：选中时间，收支type，分类type
     * 返回值：累计、平均、最高、list
     * */
    @RequestMapping(value = "/findTrendData",method = RequestMethod.POST)
    public ResponseEntity<Response<Map>> findTrendData(@RequestHeader(value = "sessionId") String sessionId,
                                                       String period,
                                                       Integer financeType,
                                                       Long categoryType) {



        Map responseData = new TreeMap<>();
        responseData.put("income","10000");
        responseData.put("average","10000");
        responseData.put("max","100000");
        responseData.put("list",new ArrayList<String>());
        responseData.put("largeList",new ArrayList<String>());
        return ResponseEntity.ok(Response.<Map>builder().flag("1").message("删除成功").data(responseData).build());

    }


}
