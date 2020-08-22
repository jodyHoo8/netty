package com.netty.c06ChannelHandler.channelHandler;


import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
    继承 ChannelInboundHandlerAdapter
    使用 ReferenceCountUtil.release(...) 来释放资源
    所以记得，每次处理消息时，都要释放它。

    SimpleChannelInboundHandler -消费入站消息更容易

    使用入站数据和释放它是一项常见的任务，Netty 为你提供了一个特殊的称为 SimpleChannelInboundHandler 的 ChannelInboundHandler 的实现。该实现将自动释放一个消息，一旦这个消息被用户通过channelRead0() 方法消费。

    当你在处理写操作，并丢弃消息时，你需要释放它。现在让我们看下实际是如何操作的。

    Listing 6.4 Handler to discard outbound data
 */
@ChannelHandler.Sharable
public class DiscardInboundHandler
        extends ChannelInboundHandlerAdapter {  //1
    @Override
    public void channelRead(ChannelHandlerContext ctx,
                            Object msg) {
        ReferenceCountUtil.release(msg); //2
    }
}

