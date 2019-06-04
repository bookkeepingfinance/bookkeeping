package com.bookkeeping.controller;

import com.bookkeeping.controller.response.Response;
import com.bookkeeping.service.CacheTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * author: liuyazong <br>
 * datetime: 2019-06-03 16:26 <br>
 * <p></p>
 */
@Slf4j
@RestController
@RequestMapping(value = {"test", ""})
public class CacheTestController {

    @Autowired
    private CacheTestService cacheTestService;

    @GetMapping
    public ResponseEntity<Response<Map<String, String>>> get(String p) {
        return ResponseEntity.ok(
                Response.<Map<String, String>>builder()
                        .flag("1")
                        .message("OK")
                        .data(cacheTestService.get(p))
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<Response<Map<String, String>>> put(String p) {
        return ResponseEntity.ok(
                Response.<Map<String, String>>builder()
                        .flag("1")
                        .message("OK")
                        .data(cacheTestService.put(p))
                        .build()
        );
    }

    @DeleteMapping
    public ResponseEntity<Response<Map<String, String>>> delete(String p) {
        return ResponseEntity.ok(
                Response.<Map<String, String>>builder()
                        .flag("1")
                        .message("OK")
                        .data(cacheTestService.delete(p))
                        .build()
        );
    }
}
