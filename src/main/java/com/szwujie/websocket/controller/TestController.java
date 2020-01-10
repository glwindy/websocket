package com.szwujie.websocket.controller;

import com.alibaba.fastjson.JSONObject;
import com.szwujie.websocket.entity.SysMessage;
import com.szwujie.websocket.entity.WiselyResponse;
import com.szwujie.websocket.service.SysMessageService;
import com.szwujie.websocket.service.WebSocketService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 说明：消息通知，若不是跟进提醒，都获取最新一条未读、未发送的消息；
 * 若是跟进管理类的消息，等到当天再提醒
 */
@Controller
public class TestController {

    @Resource
    private SysMessageService sMessageService;

    @Resource
    private SysMessageService sysMessageService;

    @Resource
    private WebSocketService webSocketService;

    @RequestMapping("/")
    public String getIndex() {
        return "index";
    }

    @RequestMapping("/getMessage")
    public String getMessage() {
        return "getmessage";
    }

    @RequestMapping("/test")
    public String getTest() {
        return "test";
    }

    @RequestMapping("/information")
    public String getInformation() {
        return "information";
    }

    @RequestMapping("/preview")
    public String getPreview() {
        return "preview";
    }

    @RequestMapping("/getPicture")
    public String getPicture() {
        return "getpicture";
    }

    /**
     * 模拟用户填写申请后即发送一个消息提醒
     * @return
     */
    @RequestMapping("/sendMessage")
    @ResponseBody
    public String getData() {
        String userId = "02976e8c-6454-4367-a872-01820f8a3960";  //消息发送者的userId
        List<String> users = new ArrayList<>();
        users.add("efd2434e-0281-4cd5-a381-80d18bf86256");  //接收消息的用户id
        //消息发起者执行任务，则立即发送消息
        SysMessage sysMessage = sysMessageService.getMessageByInitiator(userId);
        if (sysMessage != null) {
            SysMessage message = new SysMessage();
            //Gson gson = new Gson();
            message.setContent(sysMessage.getContent());
            message.setId(sysMessage.getId());
            String json = JSONObject.toJSONString(message); //gson.toJson(message);
            webSocketService.send2Users(users, new WiselyResponse(json));
        }
        return "{\"data\" : \"success\"}";
    }

    /**
     * 前端接收到消息后修改状态为已读、已发送，同时标识该接收消息的用户其他未读数据为已发送
     * @param id
     * @return
     */
    @RequestMapping("/changeById")
    @ResponseBody
    public String changeById(@RequestParam("id")String id, @RequestParam("userId")String userId) {
       // sysMessageService.changeById(id);
        //同时标记其他未能发送的数据为已发送，这样下次用户登录不会再重新发送
        //这个消息之前的都标注为放弃发送
        //sysMessageService.changeStatusByuserId(userId);
        return "{\"data\" : \"success\"}";
    }

    /**
     * 分页获取所有消息，按时间排序(接收者的userId)
     * @param userId
     * @return
     */
    @RequestMapping("/getMessageByUserId")
    @ResponseBody
    public String getMessageByUserId(@RequestParam("userId")String userId) {

        return null;
    }

    /**
     * 清除所有通知(接收者的userId)
     * @return
     */
    @RequestMapping("changeIsdelete")
    @ResponseBody
    public String changeIsdeleteById(@RequestParam("userId")String userId) {

        return null;
    }

    /**
     * 根据id查看消息的详细信息
     * @param Id
     * @return
     */
    @RequestMapping("getMessageById")
    @ResponseBody
    public String getMessageById(@RequestParam("Id")String Id) {

        return null;
    }

    /**
     * 插入消息，参数为bean
     * @param Id
     * @return
     */
    @RequestMapping("insertMessage")
    @ResponseBody
    public String insertMessage(@RequestParam("Id")String Id) {

        return null;
    }

    /**
     * 未读通知的总数(接收者的userId)
     * @return
     */
    @RequestMapping("getMessageNum")
    @ResponseBody
    public int getMessageNum(@RequestParam("userId")String userId) {

        return 0;
    }

    /**
     * 全部标为已读(接收者的userId)
     * @return
     */
    @RequestMapping("isReadMessage")
    @ResponseBody
    public String isReadMessage(@RequestParam("userId")String userId) {

        return null;
    }
}
