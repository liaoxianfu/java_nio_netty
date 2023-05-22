package com.liao.chat;


/**
 * netty 实现聊天服务启动类
 * 进行修复
 */
public class NettyChatServer implements ChatServer {
    @Override
    public void start() throws Exception {
        System.out.println("netty 服务启动");
    }

    @Override
    public void stop() throws Exception {
        // 实现其他功能
        System.out.println("netty 服务关闭");
    }
}
