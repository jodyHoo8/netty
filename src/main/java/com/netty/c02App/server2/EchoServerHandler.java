package com.netty.c02App.server2;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
    Netty 实现的 echo 服务器都需要下面这些：
    一个服务器 handler：这个组件实现了服务器的业务逻辑，决定了连接创建后和接收到信息后该如何处理
    Bootstrapping： 这个是配置服务器的启动代码。最少需要设置服务器绑定的端口，用来监听连接请求。
    通过 ChannelHandler 来实现服务器的逻辑
    Echo Server 将会将接受到的数据的拷贝发送给客户端。因此，我们需要实现 ChannelInboundHandler 接口，用来定义处理入站事件的方法。由于我们的应用很简单，只需要继承 ChannelInboundHandlerAdapter 就行了。这个类 提供了默认 ChannelInboundHandler 的实现，所以只需要覆盖下面的方法：
    channelRead() - 每个信息入站都会调用
    channelReadComplete() - 通知处理器最后的 channelread() 是当前批处理中的最后一条消息时调用
    exceptionCaught()- 读操作时捕获到异常时调用
 */
public class EchoServerHandler
        extends ChannelInboundHandlerAdapter {
    // 服务器读取的信息在这里
    @Override
    public void channelRead(ChannelHandlerContext ctx,
            Object msg) {
        ByteBuf in = (ByteBuf)msg;
        System.out.println("Server received: "+in.toString(CharsetUtil.UTF_8));
        ctx.write(in);
    }
    // 读完成以后
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)
            throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
            Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}

