package com.netty.c12Websocket.websocket;



import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;

/**
 使用Netty 的 ChannelOption 和属性
 如果每次创建通道后都不得不手动配置每个通道，这样会很麻烦，所幸，Netty提供了 ChannelOption 来帮助引导配置。这些选项都会自动的应用到引导创建的所有通道中去，可用的各种选项可以配置底层连接的详细信息，如通道“keep-alive(保持活跃)”或“timeout(超时)”的特性。

 Netty 应用程序通常会与组织或公司其他的软件进行集成，在某些情况下，Netty 的组件如 Channel 会在 Netty 正常生命周期外使用；Netty 的提供了抽象 AttributeMap 集合,这是由 Netty的管道和引导类，和AttributeKey，常见类用于插入和检索属性值。属性允许您安全的关联任何数据项与客户端和服务器的Channel。

 例如，考虑一个服务器应用程序跟踪用户和Channel之间的关系。这可以通过存储用户ID作为Channel的一个属性。类似的技术可以用来路由消息到基于用户ID或关闭基于用户活动的一个管道。

 清单9.7展示了如何使用　ChannelOption　配置　Channel　和一个属性来存储一个整数值。

 新建一个 AttributeKey 用来存储属性值
 新建 Bootstrap 用来创建客户端管道并连接他们
 指定 EventLoopGroups 从和接收到的管道来注册并获取 EventLoop
 指定 Channel 类
 设置处理器来处理管道的 I/O 和数据
 检索 AttributeKey 的属性及其值
 设置 ChannelOption 将会设置在管道在连接或者绑定
 存储 id 属性
 通过配置的 Bootstrap 来连接到远程主机
 */
public class ChannelOptionDemo {
    public static void main(String[] args) {

        // 1.新建一个 AttributeKey 用来存储属性值
        final AttributeKey<Integer> id = AttributeKey.newInstance("ID");

        // 2.新建 Bootstrap 用来创建客户端管道并连接他们
        Bootstrap bootstrap = new Bootstrap();

        // 3.指定 EventLoopGroups 从和接收到的管道来注册并获取 EventLoop
        bootstrap.group(new NioEventLoopGroup())
                // 4.指定 Channel 类
                .channel(NioSocketChannel.class)
                // 5.设置处理器来处理管道的 I/O 和数据
                .handler(new SimpleChannelInboundHandler<ByteBuf>() { //5
                    @Override
                    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                        Integer idValue = ctx.channel().attr(id).get();  //6
                        // do something  with the idValue
                    }
                    @Override
                    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                                ByteBuf byteBuf) throws Exception {
                        System.out.println("Reveived data");
                    }
                });

        // 存储 id 属性
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);   //7
        bootstrap.attr(id, 123456); //8

        // 设置 ChannelOption 将会设置在管道在连接或者绑定
        ChannelFuture future = bootstrap
                .connect(new InetSocketAddress("www.manning.com", 80));   //9
        future.syncUninterruptibly();

    }
}

