package com.szwujie.websocket.service;

import com.szwujie.websocket.entity.SysMessage;
import com.szwujie.websocket.mapper.SysMessageMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysMessageService {

    @Resource
    private SysMessageMapper sysMessageMapper;

    public SysMessage getMessageByInitiator(String userId) {
        return sysMessageMapper.getMessageByInitiator(userId);
    }

    public List<SysMessage> getMessages(String userId) {
        return sysMessageMapper.getMessages(userId);
    }

    public void changeById(String id) {
        sysMessageMapper.changeById(id);
    }

    public SysMessage getMessageByReceiver(String userId) {
        return sysMessageMapper.getMessageByReceiver(userId);
    }
}
