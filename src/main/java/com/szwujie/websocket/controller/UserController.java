package com.szwujie.websocket.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@Api(tags = "用户管理相关")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/server")
    //@ApiIgnore
    public String restServer(HttpServletRequest request){
        logger.info("This is rest server");
        // 断言
        Assert.notNull(null, "URI is required");
        return "success";
    }
}
