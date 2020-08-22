package com.netty.c12Websocket.websocket;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;

/**
    创建 EventLoopGroup 用于处理 I/O
    创建一个新的 Bootstrap 并且配置他
    最终优雅的关闭 EventLoopGroup 释放资源。这个也会关闭中当前使用的 Channel
    或者,您可以调用 Channel.close() 显式地在所有活动管道之前调用EventLoopGroup.shutdownGracefully()。但是在所有情况下,记得关闭EventLoopGroup 本身
 */
public class EventLoopGroupDemo {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup(); //1
        Bootstrap bootstrap = new Bootstrap(); //2
        bootstrap.group(group)
                .channel(NioSocketChannel.class);
        Future<?> future = group.shutdownGracefully(); //3
// block until the group has shutdown
        future.sync();
    }
}


