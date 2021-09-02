package com.example.doexcel.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * websocket服务端类
 * 在类上使用PathParam的方式'{uid}'来识别不同的连接者
 * @author huanliu
 */
@Component
@ServerEndpoint(value = "/websocket/{uid}")
public class WebSocketServer {

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "uid") String userName){
        System.out.println(userName + "连接到websocket");
    }

    @OnClose
    public void onClose(@PathParam(value = "uid") String userName){
        System.out.println(userName + "");
    }

    @OnMessage
    public void onMessage(String message) throws IOException {

    }

    @OnError
    public void onError(Session session, Throwable throwable){
        throwable.printStackTrace();
    }
}
