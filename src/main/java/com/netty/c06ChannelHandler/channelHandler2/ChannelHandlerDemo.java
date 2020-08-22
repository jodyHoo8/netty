package com.netty.c06ChannelHandler.channelHandler2;


import io.netty.channel.*;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.handler.traffic.ChannelTrafficShapingHandler;

/**
    Channel 生命周期
    Channel 有个简单但强大的状态模型，与 ChannelInboundHandler API 密切相关。下面表格是 Channel 的四个状态

    Table 6.1 Channel lifeycle states

    状态	描述
    channelUnregistered	channel已创建但未注册到一个 EventLoop.
    channelRegistered	channel 注册到一个 EventLoop.
    channelActive	channel 变为活跃状态(连接到了远程主机)，现在可以接收和发送数据了
    channelInactive	channel 处于非活跃状态，没有连接到远程主机

    channelUnregistered 已创建但未注册到一个EventLoop
    channelRegistered 注册到一个EventLoop
    channelActive 活跃状态
    channelInactive 不活跃
 */
public class ChannelHandlerDemo {
    public static void main(String[] args) {

        /**
            channelRegistered
            channelActive
            channelUnregistered
            channelNactive
         */

        ChannelHandler channelHandler = null;

        ChannelInboundHandler channelInboundHandler = null;
        ChannelOutboundHandler channelOutboundHandler = null;

        ChannelPoolHandler channelPoolHandler = null;
        ChannelDuplexHandler channelDuplexHandler;
        ChannelTrafficShapingHandler channelTrafficShapingHandler;
        ChannelPoolHandler channelPoolHandler2;
        ChannelInboundHandlerAdapter channelInboundHandlerAdapter;
        ChannelOutboundHandlerAdapter channelOutboundHandlerAdapter;

        CombinedChannelDuplexHandler combinedChannelDuplexHandler;
        SimpleChannelInboundHandler simpleChannelInboundHandler;



    }
}


