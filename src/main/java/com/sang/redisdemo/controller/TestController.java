package com.sang.redisdemo.controller;

import com.sang.redisdemo.annotation.ApiIdempotent;
import com.sang.redisdemo.service.ApiIdempotentTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mobile")
public class TestController {

    @Autowired
    private ApiIdempotentTokenService apiIdempotentTokenService;

    /**
     * 获取token
     * @return
     */
    @RequestMapping("getToken")
    public ResponseEntity<String> getToken() {
        return (ResponseEntity<String>) apiIdempotentTokenService.createToken();
    }

    /**
     * 测试接口幂等性, 在需要幂等性校验的方法上声明此注解即可
     * @return
     */
    @ApiIdempotent
    @RequestMapping("testIdempotence")
    public ResponseEntity<String> testIdempotence() {
        return ResponseEntity.ok("测试接口幂等性");
    }
}
