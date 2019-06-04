package com.bookkeeping.controller;

import com.bookkeeping.controller.response.Response;
import com.bookkeeping.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * author: liuyazong <br>
 * datetime: 2019-06-03 16:26 <br>
 * <p></p>
 */
@Slf4j
@RestController
@RequestMapping(value = {"test", ""})
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping
    public ResponseEntity<Response<Map<String, String>>> index(String p) {
        return ResponseEntity.ok(
                Response.<Map<String, String>>builder()
                        .flag("1")
                        .message("OK")
                        .data(testService.test(p))
                        .build()
        );
    }
}
