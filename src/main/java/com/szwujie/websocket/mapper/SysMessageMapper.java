package com.szwujie.websocket.mapper;

import com.szwujie.websocket.entity.SysMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysMessageMapper {
    SysMessage getMessageByInitiator(@Param("userId") String userId);

    List<SysMessage> getMessages(@Param("userId") String userId);

    void changeById(@Param("id") String id);

    SysMessage getMessageByReceiver(@Param("userId") String userId);
}
