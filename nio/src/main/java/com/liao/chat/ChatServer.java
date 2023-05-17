package com.liao.chat;


/**
 * 聊天服务接口 所有实现需要实现该类
 * 两个方法
 */
public interface ChatServer {

    /**
     * 启动聊天的服务
     *
     * @throws Exception 启动失败抛出异常
     */
    void start() throws Exception;


    /**
     * 关闭服务
     *
     * @throws Exception 关闭服务失败抛出异常
     */
    void stop() throws Exception;
}
