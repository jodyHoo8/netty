package com.netty.c02App.server;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
    echo
 */
public class EchoServer
        extends ChannelInboundHandlerAdapter {

    // 每个信息入站都会调用
    @Override
    public void channelRead(ChannelHandlerContext ctx,
                            Object msg) {
        ByteBuf in = (ByteBuf) msg;
        System.out.println("Server received: "+
                in.toString(CharsetUtil.UTF_8));
        ctx.write(in);
    }

    // 通知处理器最后的channelread()是
    // 当前批处理中最后一条消息时调用
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)
            throws Exception{
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
           .addListener(ChannelFutureListener.CLOSE);
    }

    // 捕获异常调用
    @Override
    public void
        exceptionCaught(ChannelHandlerContext ctx,
                        Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    // 通知处理器最后的 channelread() 是当前批处理中的最后一条消息时调用
    // 读操作时捕获到异常时调用

        // EchoServer
        /**
            通过 ChannelHandler 来实现服务器的逻辑
            Echo Server 将会将接受到的数据的拷贝发送给客户端。因此，我们需要实现 ChannelInboundHandler 接口，用来定义处理入站事件的方法。由于我们的应用很简单，只需要继承 ChannelInboundHandlerAdapter 就行了。这个类 提供了默认 ChannelInboundHandler 的实现，所以只需要覆盖下面的方法：

            channelRead() - 每个信息入站都会调用
            channelReadComplete() - 通知处理器最后的 channelread() 是当前批处理中的最后一条消息时调用
            exceptionCaught()- 读操作时捕获到异常时调用

            1.@Sharable 标识这类的实例之间可以在 channel 里面共享
            2.日志消息输出到控制台
            3.将所接收的消息返回给发送者。注意，这还没有冲刷数据
            4.冲刷所有待审消息到远程节点。关闭通道后，操作完成
            5.打印异常堆栈跟踪
            6.关闭通道

            这种使用 ChannelHandler 的方式体现了关注点分离的设计原则，并简化业务逻辑的迭代开发的要求。
            处理程序很简单;它的每一个方法可以覆盖到“hook（钩子）”在活动周期适当的点。很显然，我们覆盖 channelRead因为我们需要处理所有接收到的数据。
            覆盖 exceptionCaught 使我们能够应对任何 Throwable 的子类型。在这种情况下我们记录，并关闭所有可能处于未知状态的连接。它通常是难以 从连接错误中恢复，所以干脆关闭远程连接。当然，也有可能的情况是可以从错误中恢复的，所以可以用一个更复杂的措施来尝试识别和处理 这样的情况。
            如果异常没有被捕获，会发生什么？
            每个 Channel 都有一个关联的 ChannelPipeline，它代表了 ChannelHandler 实例的链。适配器处理的实现只是将一个处理方法调用转发到链中的下一个处理器。因此，如果一个 Netty 应用程序不覆盖exceptionCaught ，那么这些错误将最终到达 ChannelPipeline，并且结束警告将被记录。出于这个原因，你应该提供至少一个 实现 exceptionCaught 的 ChannelHandler。

            关键点要牢记：
            ChannelHandler 是给不同类型的事件调用
            应用程序实现或扩展 ChannelHandler 挂接到事件生命周期和 提供自定义应用逻辑。

            引导服务器
            了解到业务核心处理逻辑 EchoServerHandler 后，下面要引导服务器自身了。
            监听和接收进来的连接请求
            配置 Channel 来通知一个关于入站消息的 EchoServerHandler 实例
            Transport(传输）
            在本节中，你会遇到“transport(传输）”一词。在网络的多层视图协议里面，传输层提供了用于端至端或主机到主机的通信服务。
            互联网通信的基础是 TCP 传输。当我们使用术语“NIO transport”我们指的是一个传输的实现，
            它是大多等同于 TCP ，除了一些由 Java NIO 的实现提供了服务器端的性能增强。Transport 详细在第4章中讨论。
            Listing 2.3 EchoServer
         */

}

