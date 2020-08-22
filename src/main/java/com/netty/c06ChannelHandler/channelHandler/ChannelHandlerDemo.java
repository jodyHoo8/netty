package com.netty.c06ChannelHandler.channelHandler;


import io.netty.channel.ChannelHandler;

public class ChannelHandlerDemo {
    public static void main(String[] args) {

//        状态	描述
//        channelUnregistered	channel已创建但未注册到一个 EventLoop.
//        channelRegistered	channel 注册到一个 EventLoop.
//        channelActive	channel 变为活跃状态(连接到了远程主机)，现在可以接收和发送数据了
//        channelInactive	channel 处于非活跃状态，没有连接到远程主机


        ChannelHandler channelHandler = null;

//        ChannelHandler 子接口
//        Netty 提供2个重要的 ChannelHandler 子接口：
//
//        ChannelInboundHandler - 处理进站数据和所有状态更改事件
//        ChannelOutboundHandler - 处理出站数据，允许拦截各种操作
//        ChannelHandler 适配器
//
//        Netty 提供了一个简单的 ChannelHandler 框架实现，给所有声明方法签名。这个类 ChannelHandlerAdapter 的方法,主要推送事件 到 pipeline 下个 ChannelHandler 直到 pipeline 的结束。这个类 也作为 ChannelInboundHandlerAdapter 和ChannelOutboundHandlerAdapter 的基础。所有三个适配器类的目的是作为自己的实现的起点;您可以扩展它们,覆盖你需要自定义的方法。

    }
}


