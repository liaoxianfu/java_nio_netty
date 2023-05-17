package com.liao.chat;


/**
 * netty 实现聊天服务启动类
 */
public class NettyChatServer implements ChatServer {
    @Override
    public void start() throws Exception {
        System.out.println("netty 服务启动");
    }

    @Override
    public void stop() throws Exception {
        System.out.println("netty 服务关闭");
    }
}
