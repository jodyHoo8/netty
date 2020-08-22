package com.netty.c06ChannelHandler.channelHandler;


import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;


/**
    继承 ChannelOutboundHandlerAdapter
    使用 ReferenceCountUtil.release(...) 来释放资源
    通知 ChannelPromise 数据已经被处理
    重要的是，释放资源并通知 ChannelPromise。如果，ChannelPromise 没有被通知到，这可能会引发 ChannelFutureListener 不会被处理的消息通知的状况。

    所以，总结下：如果消息是被 消耗/丢弃 并不会被传入下个 ChannelPipeline 的 ChannelOutboundHandler ，调用 ReferenceCountUtil.release(message) 。一旦消息经过实际的传输，在消息被写或者 Channel 关闭时，它将会自动释放。


    泄漏检测等级
    Netty 现在定义了四种泄漏检测等级，可以按需开启，见下表

    Table 6.5 Leak detection levels

    Level Description	DISABLED
    Disables	Leak detection completely. While this even eliminates the 1 % overhead you should only do this after extensive testing.
    SIMPLE	Tells if a leak was found or not. Again uses the sampling rate of 1%, the default level and a good fit for most cases.
    ADVANCED	Tells if a leak was found and where the message was accessed, using the sampling rate of 1%.
    PARANOID	Same as level ADVANCED with the main difference that every access is sampled. This it has a massive impact on performance. Use this only in the debugging phase.


    ChannelOutboundHandler
    ChannelOutboundHandler 提供了出站操作时调用的方法。这些方法会被 Channel, ChannelPipeline, 和 ChannelHandlerContext 调用。
    ChannelOutboundHandler 另个一个强大的方面是它具有在请求时延迟操作或者事件的能力。比如，当你在写数据到 remote peer 的过程中被意外暂停，你可以延迟执行刷新操作，然后在迟些时候继续。
    下面显示了 ChannelOutboundHandler 的方法（继承自 ChannelHandler 未列出来）

    Table 6.4 ChannelOutboundHandler methods

    类型	描述
    bind	Invoked on request to bind the Channel to a local address
    connect	Invoked on request to connect the Channel to the remote peer
    disconnect	Invoked on request to disconnect the Channel from the remote peer
    close	Invoked on request to close the Channel
    deregister	Invoked on request to deregister the Channel from its EventLoop
    read	Invoked on request to read more data from the Channel
    flush	Invoked on request to flush queued data to the remote peer through the Channel
    write	Invoked on request to write data through the Channel to the remote peer
    几乎所有的方法都将 ChannelPromise 作为参数,一旦请求结束要通过 ChannelPipeline 转发的时候，必须通知此参数。

    ChannelPromise vs. ChannelFuture
    ChannelPromise 是 特殊的 ChannelFuture，允许你的 ChannelPromise 及其 操作 成功或失败。所以任何时候调用例如 Channel.write(...) 一个新的 ChannelPromise将会创建并且通过 ChannelPipeline传递。这次写操作本身将会返回 ChannelFuture， 这样只允许你得到一次操作完成的通知。Netty 本身使用 ChannelPromise 作为返回的 ChannelFuture 的通知，事实上在大多数时候就是 ChannelPromise 自身（ChannelPromise 扩展了 ChannelFuture）
    如前所述,ChannelOutboundHandlerAdapter 提供了一个实现了 ChannelOutboundHandler 所有基本方法的实现的框架。 这些简单事件转发到下一个 ChannelOutboundHandler 管道通过调用 ChannelHandlerContext 相关的等效方法。你可以根据需要自己实现想要的方法。

    资源管理
    当你通过 ChannelInboundHandler.channelRead(...) 或者 ChannelOutboundHandler.write(...) 来处理数据，重要的是在处理资源时要确保资源不要泄漏。
    Netty 使用引用计数器来处理池化的 ByteBuf。所以当 ByteBuf 完全处理后，要确保引用计数器被调整。
    引用计数的权衡之一是用户时必须小心使用消息。当 JVM 仍在 GC(不知道有这样的消息引用计数)这个消息，以至于可能是之前获得的这个消息不会被放回池中。因此很可能,如果你不小心释放这些消息，很可能会耗尽资源。
    为了让用户更加简单的找到遗漏的释放，Netty 包含了一个 ResourceLeakDetector ，将会从已分配的缓冲区 1% 作为样品来检查是否存在在应用程序泄漏。因为 1% 的抽样,开销很小。
    对于检测泄漏,您将看到类似于下面的日志消息。
 */
@ChannelHandler.Sharable
public class DiscardOutboundHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx,
                      Object msg,
                      ChannelPromise promise) {
        ReferenceCountUtil.release(msg);  //2
        promise.setSuccess();    //3
    }
}

