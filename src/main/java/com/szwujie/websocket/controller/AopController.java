package com.szwujie.websocket.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aop")
public class AopController {

    @GetMapping(value = "/test")
    public String testAop(String key) {
        return "key=" + key;
    }

    @GetMapping("testAfterThrowing")
    public String testAfterThrowing(String key) {
        throw new NullPointerException();
    }

    @RequestMapping("/testAround")
    public String testAround(String key){
        return "key=" + key;
    }
}
