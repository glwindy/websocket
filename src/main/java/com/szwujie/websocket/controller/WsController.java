package com.szwujie.websocket.controller;

import com.alibaba.fastjson.JSONObject;
import com.szwujie.websocket.entity.SysMessage;
import com.szwujie.websocket.entity.WiselyMessage;
import com.szwujie.websocket.entity.WiselyResponse;
import com.szwujie.websocket.consts.Constant;
import com.szwujie.websocket.service.SysMessageService;
import com.szwujie.websocket.service.WebSocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
public class WsController {

    private Logger logger = LoggerFactory.getLogger(WsController.class);

    @Resource
    private WebSocketService webSocketService;

    @Resource
    private SysMessageService sysMessageService;

    @MessageMapping(Constant.FORETOSERVERPATH) //@MessageMapping和@RequestMapping功能类似，用于设置URL映射地址，浏览器向服务器发起请求，需要通过该地址。
    //@RequestMapping(value = Constant.FORETOSERVERPATH, method = RequestMethod.GET)
    @SendTo(Constant.PRODUCERPATH)//如果服务器接受到了消息，就会对订阅了@SendTo括号中的地址传送消息。
    public WiselyResponse say(WiselyMessage message) throws Exception {
        logger.info("getUser: {}", message.getUserId());   //接收消息的userId
        SysMessage sysMessage = sysMessageService.getMessageByReceiver(message.getUserId());
        if (sysMessage != null) {
            List<String> users = new ArrayList<>();
            users.add(message.getUserId());
            //Gson gson = new Gson();
            SysMessage smessage = new SysMessage();
            smessage.setContent(sysMessage.getContent());
            smessage.setId(sysMessage.getId());
            String json = JSONObject.toJSONString(smessage); //gson.toJson(smessage);
            webSocketService.sentMessageToUser(message.getUserId(), json);
            //webSocketService.send2Users(users, new WiselyResponse(json));
        }
        return new WiselyResponse("Welcome, " + message.getName() + "!");
    }

    @PostMapping("/webtest")
    public String webtest(@RequestParam("userId")String userId, @RequestParam("name")String name) {
        logger.info("{} : {}", userId, name);
        return "{\"data\":\"success\"}";
    }

}
