package com.netty.c10Bootstrap;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

import java.net.InetSocketAddress;

/**
    Netty中的Bootstrap 类型
    Netty中的引导类型有两种。不仅仅是当作的“服务器”和“客户”的引导，
    更是要考虑他们的目的能支持的应用程序的功能。从这个意义上讲,“服务器”应用程序把一个“父”管道接受连接和创建“子”管道,而“客户端”很可能只需要一个单一的、
    非“父”对所有网络交互的管道（对于无连接的比如 UDP 协议也是一样）。
    如图9.1所示,两个引导实现自一个名为 AbstractBootstrap 的超类。

    Figure 9.1 Bootstrap hierarchy
    前面的章节介绍的许多我们共同关注的话题,同样适用于客户端和服务器。这些都是由 AbstractBootstrap 处理,
    从而防止重复的功能和代码。专业引导类可以完全专注于它们独特的需要关心的地方。
    克隆引导类
    我们经常需要创建多个通道具有相似或相同的设置。支持这种模式而不需要为每个通道创建和配置一个新的引导实例,
    AbstractBootstrap 已经被标记为 Cloneable。调用 clone() 在一个已经配置引导将返回另一个引导实例并且是立即可用。
    注意,因为这将创建只是 EventLoopGroup 浅拷贝,后者将会共享所有的克隆管道。这是可以接受的,
    因为往往是克隆的管道是 short-lived(短暂的，典型示例是管道创建用于 HTTP 请求)
    下面内容将会关注 Bootstrap 和 ServerBootstrap

    继承 ByteToMessageDecoder 用来处理入站的字节并将他们解码为消息
    指定产出的帧的长度
    检查是否有足够的字节用于读到下个帧
    从 ByteBuf 读取新帧
    添加帧到解码好的消息 List
 */
public class BootstrapType {
    public static void main(String[] args) throws InterruptedException {

        ServerBootstrap bootstrap = new ServerBootstrap();//1
        bootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup())  //2
                .channel(NioServerSocketChannel.class)  //3
                .childHandler(new ChannelInitializer(){
                    @Override
                    protected void initChannel(Channel channel) throws Exception {

                    }
                }); //4
        ChannelFuture future = bootstrap.bind(new InetSocketAddress(8080));  //5
        future.sync();

        final class ChannelInitializerImpl extends ChannelInitializer<Channel> {  //6
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline(); //7
                pipeline.addLast(new HttpClientCodec());
                pipeline.addLast(new HttpObjectAggregator(Integer.MAX_VALUE));

            }
        }

    }
}
