package com.netty.c06ChannelHandler.channelHandler;


import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
    1.扩展 SimpleChannelInboundHandler
    2.不需做特别的释放资源的动作
 */
@ChannelHandler.Sharable
public class SimpleDiscardHandler
        extends SimpleChannelInboundHandler<Object> {  //1
    @Override
    public void channelRead0(ChannelHandlerContext ctx,
                             Object msg) {
        // No need to do anything special //2
    }
}
