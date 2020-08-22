package com.netty.c06ChannelHandler.channelHandler2;


import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
    1.扩展 SimpleChannelInboundHandler
    2.不需做特别的释放资源的动作

    使用构造函数来传递 SSLContext 用于使用(startTls 是否启用)
    从 SslContext 获得一个新的 SslEngine 。给每个 SslHandler 实例使用一个新的 SslEngine
    设置 SslEngine 是 client 或者是 server 模式
    添加 SslHandler 到 pipeline 作为第一个处理器
 */
@ChannelHandler.Sharable
public class SimpleDiscardHandler extends SimpleChannelInboundHandler<Object> {
    @Override
    public void channelRead0(ChannelHandlerContext ctx,
                             Object msg) {
        // No need to do anything special //2
    }
}

