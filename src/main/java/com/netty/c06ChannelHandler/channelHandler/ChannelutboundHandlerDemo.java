package com.netty.c06ChannelHandler.channelHandler;



/**
    ChannelOutboundHandler
    ChannelOutboundHandler 提供了出站操作时调用的方法。
    这些方法会被 Channel, ChannelPipeline, 和 ChannelHandlerContext 调用。
    ChannelOutboundHandler
    另个一个强大的方面是它具有在请求时延迟操作或者事件的能力。
    比如，当你在写数据到 remote peer 的过程中被意外暂停，你可以延迟执行刷新操作，
    然后在迟些时候继续。

    类型	描述
    bind	Invoked on request to bind the Channel to a local address
    connect	Invoked on request to connect the Channel to the remote peer
    disconnect	Invoked on request to disconnect the Channel from the remote peer
    close	Invoked on request to close the Channel
    deregister	Invoked on request to deregister the Channel from its EventLoop
    read	Invoked on request to read more data from the Channel
    flush	Invoked on request to flush queued data to the remote peer through the Channel
    write	Invoked on request to write data through the Channel to the remote peer

    资源管理
    当你通过 ChannelInboundHandler.channelRead(...) 或者 ChannelOutboundHandler.write(...) 来处理数据，重要的是在处理资源时要确保资源不要泄漏。
    Netty 使用引用计数器来处理池化的 ByteBuf。所以当 ByteBuf 完全处理后，要确保引用计数器被调整。
    引用计数的权衡之一是用户时必须小心使用消息。当 JVM 仍在 GC(不知道有这样的消息引用计数)这个消息，以至于可能是之前获得的这个消息不会被放回池中。因此很可能,如果你不小心释放这些消息，很可能会耗尽资源。
    为了让用户更加简单的找到遗漏的释放，Netty 包含了一个 ResourceLeakDetector ，将会从已分配的缓冲区 1% 作为样品来检查是否存在在应用程序泄漏。因为 1% 的抽样,开销很小。
    对于检测泄漏,您将看到类似于下面的日志消息。

    泄漏检测等级
    Netty 现在定义了四种泄漏检测等级，可以按需开启，见下表
    Table 6.5 Leak detection levels

    Table 6.5 Leak detection levels
    Level Description	DISABLED
    Disables	Leak detection completely. While this even eliminates the 1 % overhead you should only do this after extensive testing.
    SIMPLE	Tells if a leak was found or not. Again uses the sampling rate of 1%, the default level and a good fit for most cases.
    ADVANCED	Tells if a leak was found and where the message was accessed, using the sampling rate of 1%.
    PARANOID	Same as level ADVANCED with the main difference that every access is sampled. This it has a massive impact on performance. Use this only in the debugging phase.
 */
public class ChannelutboundHandlerDemo {
    public static void main(String[] args) {



    }
}

