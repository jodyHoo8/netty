package com.netty.c02App.server3;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

@Sharable
public class EchoClientHandler
        extends SimpleChannelInboundHandler<ByteBuf> {
    // 1.服务器的连接被建立后调用 一次
    //   一旦建立了连接，字节序列被发送到服务器。该消息的内容并不重要;在这里，
    //   我们使用了 Netty 编码字符串 “Netty rocks!”
    //   通过覆盖这种方法，我们确保东西被尽快写入到服务器。
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 1.2.一步
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!",
                CharsetUtil.UTF_8));
        // 1.2.两步
//        ctx.write(Unpooled.copiedBuffer("Netty rocks!",
//                CharsetUtil.UTF_8));
//        ctx.flush();
    }
    // 2.数据后从服务器接收到调用
    @Override
    public void channelRead0(ChannelHandlerContext ctx,
                             ByteBuf in) {
        System.out.println("Client received: "+
                            in.toString(CharsetUtil.UTF_8));
    }
    // 3.捕获一个异常时调用
    //   SimpleChannelInboundHandler vs. ChannelInboundHandler
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}

