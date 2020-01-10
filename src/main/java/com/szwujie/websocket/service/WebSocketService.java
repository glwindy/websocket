package com.szwujie.websocket.service;

import com.szwujie.websocket.entity.WiselyResponse;
import com.szwujie.websocket.consts.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WebSocketService {
    @Autowired
    private SimpMessagingTemplate template;

    /**
     * 广播
     * 发给所有在线用户
     *
     * @param msg
     */
    public void sendMsg(WiselyResponse msg) {
        template.convertAndSend(Constant.PRODUCERPATH, msg);
    }

    /**
     * 发送给指定用户
     * @param users
     * @param msg
     */
    public void send2Users(List<String> users, WiselyResponse msg) {
        //logger.info("userId：" + users.get(0));

        users.forEach(userName -> {
            template.convertAndSendToUser(userName, Constant.P2PPUSHPATH, msg);
        });
    }


    public void sentMessageToUser(String userId, String message) {
        List<String> users = new ArrayList<>();
        users.add(userId);
        WiselyResponse msg = new WiselyResponse(message);
        users.forEach(userName -> {
            template.convertAndSendToUser(userName, Constant.P2PPUSHPATH, msg);
        });
    }
}
