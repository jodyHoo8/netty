package com.netty.c02App.server3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
    引导服务器
    EchoServerHandler
    -- 监听接收进来的请求
    -- 配置Channel 通知一个关于入站消息的 EchoServerHandler

    Transport 传输
    网络多层视图 协议
    传输层 提供了 用于 端 或主机到主机的通信服务

    TCP传输


    1.设置端口值（抛出一个 NumberFormatException 如果该端口参数的格式不正确）
    2.呼叫服务器的 start() 方法
    3.创建 EventLoopGroup
    4.创建 ServerBootstrap
    5.指定使用 NIO 的传输 Channel
    6.设置 socket 地址使用所选的端口
    7.添加 EchoServerHandler 到 Channel 的 ChannelPipeline
    8.绑定的服务器;sync 等待服务器关闭
    9.关闭 channel 和 块，直到它被关闭
    10.关机的 EventLoopGroup，释放所有资源。


    EchoServerHandler 实现了的业务逻辑
    在 main() 方法，引导了服务器
    执行后者所需的步骤是：

    创建 ServerBootstrap 实例来引导服务器并随后绑定
    创建并分配一个 NioEventLoopGroup 实例来处理事件的处理，如接受新的连接和读/写数据。
    指定本地 InetSocketAddress 给服务器绑定
    通过 EchoServerHandler 实例给每一个新的 Channel 初始化
    最后调用 ServerBootstrap.bind() 绑定服务器
 */
public class EchoServer {
    // 1.set port
    private final int port;
    public EchoServer(int port) {
        this.port = port;
    }
    public static void main(String[] args)
        throws Exception {
        if (args.length!=1) {
            System.err.println(
                    "Usage: " + EchoServer.class.getSimpleName() +
                            " <port>");
            return;
        }
        int port = Integer.parseInt(args[0]);
        // 2.call start method
        new EchoServer(port).start();
    }
    public void start() throws Exception {
        // 3.开启NioEventLoopGroup 对象
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            // 4.新建ServerBootstrap 对象
            ServerBootstrap b = new ServerBootstrap();
            // 5.配置 eventloopgroup
            //        channel渠道
            //   绑定 localAddress 本地地址 -- 跟 ServerSocketChannel 一样
            //   设置 childHandler 新建 ChannelInitializer 渠道初始化
            //                     重写 initChannel
            b.group(group)
             .channel(NioServerSocketChannel.class)
             .localAddress(new InetSocketAddress(port))
             .childHandler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 public void initChannel(SocketChannel ch)
                        throws Exception {
                     // 5.2.在新建ChannelInitializer的时候addList方式把ServerHandler加入
                     ch.pipeline().addLast(
                         new EchoServerHandler()
                     );
                 }
             });
            // 6.使用Bootstrap 做 bind 绑定并 用 sync
            ChannelFuture future = b.bind().sync();
            System.out.println(EchoServer.class.getName()+
                              "started and listen on: "+
                               future.channel().localAddress());
            // 7.使用Bootstrap bind 同步绑定而生成的 ChannelFuture
            //   同步 关闭channel 关闭future
            future.channel().closeFuture().sync();
        } finally {
            // shutdown eventLoopGroup gracefully
            // 8.同步 优雅 地关闭 eventLoopGroup
            group.shutdownGracefully().sync();
        }
    }
}

