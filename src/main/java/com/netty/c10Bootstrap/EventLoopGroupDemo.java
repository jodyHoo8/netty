package com.netty.c10Bootstrap;


import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
    创建一个新的 Bootstrap 来创建和连接到新的客户端管道
    指定 EventLoopGroup
    指定 Channel 实现来使用
    设置处理器给 Channel 的事件和数据
    连接到远端主机


    兼容性
    Channel 的实现和 EventLoop 的处理过程在 EventLoopGroup 中必须兼容，哪些 Channel 是和 EventLoopGroup 是兼容的可以查看 API 文档。经验显示，相兼容的实现一般在同一个包下面，例如使用NioEventLoop，NioEventLoopGroup 和 NioServerSocketChannel 在一起。请注意，这些都是前缀“Nio”，然后不会用这些代替另一个实现和另一个前缀，如“Oio”，也就是说 OioEventLoopGroup 和NioServerSocketChannel 是不相容的。
    Channel 和 EventLoopGroup 的 EventLoop 必须相容，例如NioEventLoop、NioEventLoopGroup、NioServerSocketChannel是相容的，但是 OioEventLoopGroup 和 NioServerSocketChannel 是不相容的。从类名可以看出前缀是“Nio”的只能和“Nio”的一起使用。
    EventLoop 和 EventLoopGroup
    记住,EventLoop 分配给该 Channel 负责处理 Channel 的所有操作。当你执行一个方法,该方法返回一个 ChannelFuture ，它将在 分配给 Channel 的 EventLoop 执行。
    EventLoopGroup 包含许多 EventLoops 和分配一个 EventLoop 通道时注册。我们将在15章更详细地讨论这个话题。
    清单9.2所示的结果,试图使用一个 Channel 类型与一个 EventLoopGroup 兼容。


    创建新的 Bootstrap 来创建新的客户端管道
    注册 EventLoopGroup 用于获取 EventLoop
    指定要使用的 Channel 类。通知我们使用 NIO 版本用于 EventLoopGroup ， OIO 用于 Channel
    设置处理器用于管道的 I/O 事件和数据
    尝试连接到远端。当 NioEventLoopGroup 和 OioSocketChannel 不兼容时，会抛出 IllegalStateException 异常

    group()
    channel() 或 channnelFactory()
    handler()

    如何引导客户端
    Bootstrap 类负责创建管道给客户或应用程序，利用无连接协议和在调用 bind() 或 connect() 之后。
 */
public class EventLoopGroupDemo {
    public static void main(String[] args) {


        /**
            创建一个新的 Bootstrap 来创建和连接到新的客户端管道
            指定 EventLoopGroup
            指定 Channel 实现来使用
            设置处理器给 Channel 的事件和数据
            连接到远端主机

            group()
            channel() 或 channnelFactory()
            handler()
         */
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap(); //1

        bootstrap.group(group) //2
                .channel(NioSocketChannel.class) //3
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void
                    channelRead0(ChannelHandlerContext channelHandlerContext,
                                 ByteBuf byteBuf) throws Exception {
                        System.out.println("Received data");
                        byteBuf.clear();
                    } //4
                });


        ChannelFuture future = bootstrap.connect(
                new InetSocketAddress("www.manning.com", 80)); //5

        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture)
                    throws Exception {
                if (channelFuture.isSuccess()) {
                    System.out.println("Connection established");
                } else {
                    System.err.println("Connection attempt failed");
                    channelFuture.cause().printStackTrace();
                }
            }
        });

    }
}

