package com.tsunglin.tsunglin00.websocket.controller;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.tsunglin.tsunglin00.websocket.utils.WebSocketUtil;

//ws://localhost:28019/WebSocketServer/tsunglin

@Component
@ServerEndpoint(value = "/WebSocketServer/{usernick}")
public class WebSocketController {

    @OnOpen
    public void onOpen(@PathParam(value = "usernick") String userNick, Session session) {
        String message = "有新成員[" + userNick + "]加入聊天室!";
        String message1 = "connected";
        System.out.println(message+" "+session);
        WebSocketUtil.addSession(userNick, session);
        WebSocketUtil.sendMessageForAll(message1);
    }

    @OnClose
    public void onClose(@PathParam(value = "usernick") String userNick, Session session) {
        String message = "成員[" + userNick + "]退出聊天室!";
        System.out.println(message);
        WebSocketUtil.remoteSession(userNick);
        WebSocketUtil.sendMessageForAll(message);
    }

    @SneakyThrows
    @OnMessage
    public void OnMessage(@PathParam(value = "usernick") String userNick, String message, Session session) {
        String info = "成員[" + userNick + "]：" + message;
        JSONObject ob = new JSONObject(message);
        String userid = ob.getString("userid");
        String msgtext = ob.getString("msgtext");
        System.out.println("userid= "+userid+"msgtext= "+msgtext);
        String message1 = "send:success";
        System.out.println(info);
        WebSocketUtil.sendMessageForAll(message1);
        //OnMessage1("{'cmd':'send','userid':'0000001', 'msgtext':'aaa'}");
        Thread.sleep(200);
        WebSocketUtil.sendMessageForAll("{'cmd':'send','userid':'0000001', 'msgtext':'"+msgtext+"'}");
        //return "{'cmd':'send','userid':'0000001', 'msgtext':'aaa'}";
    }

    public String OnMessage1(String message) {
        return message;
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("錯誤:" + throwable);
        try {
            session.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        throwable.printStackTrace();
    }

}
