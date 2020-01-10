package com.szwujie.websocket.controller;

import com.szwujie.websocket.utils.validate.WebDesc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aop2")
public class AopTestController {

    @GetMapping("/testAnnotation")
    @WebDesc(describe = "This is testAnnotation Controller")
    public String testAnnotation(String key){
        return "key = " + key;
    }

}
