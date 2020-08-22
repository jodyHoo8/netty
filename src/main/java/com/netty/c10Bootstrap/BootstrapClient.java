package com.netty.c10Bootstrap;


import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

// childHandler(), childAttr() 和 childOption()

/**
    ServerBootstrap 中的
    childHandler(),
    childAttr() 和
    childOption() 是常用的服务器应用的操作。
    具体来说,ServerChannel实现负责创建子 Channel,它代表接受连接。
    因此 引导 ServerChannel 的 ServerBootstrap ,
    提供这些方法来简化接收的 Channel 对 ChannelConfig 应用设置的任务。
    图9.3显示了
    ServerChannel
    创建 ServerBootstrap 在
    bind(),后者管理大量的子 Channel

    当调用 bind() 后 ServerBootstrap 将创建一个新的管道，这个管道将会在绑定成功后接收子管道
    接收新连接给每个子管道
    接收连接的 Channel

    Figure 9.3 ServerBootstrap
    记住 child* 的方法都是操作在子的 Channel，被 ServerChannel 管理。
    清单9.4 ServerBootstrap 时会创建一个 NioServerSocketChannel实例 bind() 。这个 NioServerChannel 负责接受新连接和创建NioSocketChannel 实例。


    当 bind() 调用时，ServerBootstrap 创建一个新的ServerChannel 。 当绑定成功后，这个管道就能接收子管道了
    ServerChannel 接收新连接并且创建子管道来服务它们
    Channel 用于接收到的连接
    管道自己创建了 Bootstrap，用于当 connect() 调用时创建一个新的管道
    新管道连接到远端
    在 EventLoop 接收通过 connect() 创建后就在管道间共享

    创建一个新的 ServerBootstrap 来创建新的 SocketChannel 管道并且绑定他们
    指定 EventLoopGroups 从 ServerChannel 和接收到的管道来注册并获取 EventLoops
    指定 Channel 类来使用
    设置处理器用于处理接收到的管道的 I/O 和数据
    创建一个新的 Bootstrap 来连接到远程主机
    设置管道类
    设置处理器来处理 I/O
    使用相同的 EventLoop 作为分配到接收的管道
    连接到远端
    连接完成处理业务逻辑 (比如, proxy)
    通过配置了的 Bootstrap 来绑定到管道


    Netty 在一个引导中添加多个 ChannelHandler
    在所有的例子代码中，我们在引导过程中通过 handler() 或childHandler() 都只添加了一个 ChannelHandler 实例，对于简单的程序可能足够，但是对于复杂的程序则无法满足需求。例如，某个程序必须支持多个协议，如 HTTP、WebSocket。若在一个 ChannelHandle r中处理这些协议将导致一个庞大而复杂的 ChannelHandler。Netty 通过添加多个 ChannelHandler，从而使每个 ChannelHandler 分工明确，结构清晰。
    Netty 的一个优势是可以在 ChannelPipeline 中堆叠很多ChannelHandler 并且可以最大程度的重用代码。如何添加多个ChannelHandler 呢？Netty 提供 ChannelInitializer 抽象类用来初始化 ChannelPipeline 中的 ChannelHandler。ChannelInitializer是一个特殊的 ChannelHandler，通道被注册到 EventLoop 后就会调用ChannelInitializer，并允许将 ChannelHandler 添加到CHannelPipeline；完成初始化通道后，这个特殊的 ChannelHandler 初始化器会从 ChannelPipeline 中自动删除。
 */
public class BootstrapClient {
    public static void main(String[] args) {

        ServerBootstrap bootstrap = new ServerBootstrap(); //1
        bootstrap.group(new NioEventLoopGroup(), //2
                new NioEventLoopGroup()).channel(NioServerSocketChannel.class) //3
                .childHandler(        //4
                        new SimpleChannelInboundHandler<ByteBuf>() {
                            ChannelFuture connectFuture;
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                Bootstrap bootstrap = new Bootstrap();//5
                                bootstrap.channel(NioSocketChannel.class) //6
                                        .handler(new SimpleChannelInboundHandler<ByteBuf>() {  //7
                                            @Override
                                            protected void channelRead0(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
                                                System.out.println("Reveived data");
                                            }
                                        });
                                bootstrap.group(ctx.channel().eventLoop()); //8
                                connectFuture = bootstrap.connect(new InetSocketAddress("www.manning.com", 80));  //9
                            }
                            @Override
                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
                                if (connectFuture.isDone()) {
                                    // do something with the data  //10
                                }
                            }
                        });
        ChannelFuture future = bootstrap.bind(new InetSocketAddress(8080));  //11
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    System.out.println("Server bound");
                } else {
                    System.err.println("Bound attempt failed");
                    channelFuture.cause().printStackTrace();
                }
            }
        });
    }
}

