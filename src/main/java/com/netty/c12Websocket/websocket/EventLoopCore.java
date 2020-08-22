package com.netty.c12Websocket.websocket;


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

/**
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
 */
public class EventLoopCore {
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


