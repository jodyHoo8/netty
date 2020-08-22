package com.netty.c02App.server2;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
    1.@Sharable 标识这类的实例之间可以在 channel 里面共享
    2.日志消息输出到控制台
    3.将所接收的消息返回给发送者。注意，这还没有冲刷数据
    4.冲刷所有待审消息到远程节点。关闭通道后，操作完成
    5.打印异常堆栈跟踪
    6.关闭通道

    -- ChannelHandler 是给不同类型的事件调用
    -- 应用程序实现或扩展 ChannelHandler
       挂接到事件生命周期和 提供自定义应用逻辑。

    引导服务器
    了解到业务核心处理逻辑 EchoServerHandler 后，下面要引导服务器自身了。

    监听和接收进来的连接请求
    配置 Channel 来通知一个关于入站消息的
    EchoServerHandler 实例


    Transport(传输）
    在本节中，你会遇到“transport(传输）”一词。
    在网络的多层视图协议里面，传输层提供了用于端至端或主机到
    主机的通信服务。互联网通信的基础是 TCP 传输。
    当我们使用术语“NIO transport”我们指的是一个传输的实现，
    它是大多等同于 TCP ，
    除了一些由 Java NIO 的实现提供了服务器端的性能增强。
    Transport 详细在第4章中讨论。


    1.设置端口值（抛出一个 NumberFormatException
      如果该端口参数的格式不正确）
    2.呼叫服务器的 start() 方法
    3.创建 EventLoopGroup
    4.创建 ServerBootstrap
    5.指定使用 NIO 的传输 Channel
    6.设置 socket 地址使用所选的端口
    7.添加 EchoServerHandler 到 Channel 的 ChannelPipeline
    8.绑定的服务器;sync 等待服务器关闭
    9.关闭 channel 和 块，直到它被关闭
    10.关机的 EventLoopGroup，释放所有资源。

    在这个例子中，代码创建 ServerBootstrap 实例（步骤4）。由于我们使用在 NIO 传输，我们已指定 NioEventLoopGroup（3）接受和处理新连接，指定 NioServerSocketChannel（5）为信道类型。在此之后，我们设置本地地址是 InetSocketAddress 与所选择的端口（6）如。服务器将绑定到此地址来监听新的连接请求。
    第7步是关键：在这里我们使用一个特殊的类，ChannelInitializer 。当一个新的连接被接受，一个新的子 Channel 将被创建， ChannelInitializer 会添加我们EchoServerHandler 的实例到 Channel 的 ChannelPipeline。正如我们如前所述，如果有入站信息，这个处理器将被通知。
    虽然 NIO 是可扩展性，但它的正确配置是不简单的。特别是多线程，要正确处理也非易事。幸运的是，Netty 的设计封装了大部分复杂性，尤其是通过抽象，例如 EventLoopGroup，SocketChannel 和 ChannelInitializer，其中每一个将在更详细地在第3章中讨论。
    在步骤8，我们绑定的服务器，等待绑定完成。 （调用 sync() 的原因是当前线程阻塞）在第9步的应用程序将等待服务器 Channel 关闭（因为我们 在 Channel 的 CloseFuture 上调用 sync()）。现在，我们可以关闭下 EventLoopGroup 并释放所有资源，包括所有创建的线程（10）。
    NIO 用于在本实施例，因为它是目前最广泛使用的传输，归功于它的可扩展性和彻底的不同步。但不同的传输的实现是也是可能的。例如，如果本实施例中使用的 OIO 传输，我们将指定 OioServerSocketChannel 和 OioEventLoopGroup。 Netty 的架构，包括更关于传输信息，将包含在第4章。在此期间，让我们回顾下在服务器上执行，我们只研究重要步骤。

    服务器的主代码组件是

    EchoServerHandler 实现了的业务逻辑
    在 main() 方法，引导了服务器
    执行后者所需的步骤是：

    创建 ServerBootstrap 实例来引导服务器并随后绑定
    创建并分配一个 NioEventLoopGroup 实例来处理事件的处理，
    如接受新的连接和读/写数据。
    指定本地 InetSocketAddress 给服务器绑定
    通过 EchoServerHandler 实例给每一个新的 Channel 初始化
    最后调用 ServerBootstrap.bind() 绑定服务器
    这样服务器的初始化就完成了，并可以被使用。

 */
public class EchoServer {
    private int port;
    public EchoServer(int port) {
        this.port = port;
    }
    public static void main(String[] args)
            throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: "+
                                EchoServer.class.getSimpleName()+
                               "<port>");
            return;
        }
        int port = Integer.parseInt(args[0]);
        new EchoServer(port).start();
    }
    public void start() throws Exception {
        // 1.事件驱动 --
        NioEventLoopGroup group = new NioEventLoopGroup();
        // 2.bootstrap
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(group)                                //4
                     .channel(NioServerSocketChannel.class)        //5
                     .localAddress(new InetSocketAddress(port))    //6
                     .childHandler(new ChannelInitializer<SocketChannel>() { //7
                         @Override
                         public void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline().addLast(
                                    new EchoServerHandler());
                         }
                     });
            ChannelFuture channelFuture =
                    bootstrap.bind().sync();
            System.out.println(EchoServer.class.getName() +
                              " started and listen on " +
                               channelFuture.channel().localAddress());
            channelFuture.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }
}

