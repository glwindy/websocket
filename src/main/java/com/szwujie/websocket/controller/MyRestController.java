package com.szwujie.websocket.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/rest")
public class MyRestController {
    private static final Logger logger = LoggerFactory.getLogger(MyRestController.class);

    @Autowired
    private RestTemplate restTemplate;

    private static final String URL = "http://localhost:8500/user/server";

    @GetMapping("/default")
    public void defaultRestClient(){
        String result = restTemplate.getForObject(URL, String.class);
        logger.info("result = {}",result);
    }

}
