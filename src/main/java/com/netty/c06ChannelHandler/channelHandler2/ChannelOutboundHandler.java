package com.netty.c06ChannelHandler.channelHandler2;


import com.netty.c06ChannelHandler.channelHandler.DiscardHandler;
import io.netty.channel.ChannelPipeline;

/**
    ChannelOutboundHandler
    ChannelOutboundHandler 提供了出站操作时调用的方法。这些方法会被 Channel, ChannelPipeline, 和 ChannelHandlerContext 调用。
    ChannelOutboundHandler 另个一个强大的方面是它具有在请求时延迟操作或者事件的能力。比如，当你在写数据到 remote peer 的过程中被意外暂停，你可以延迟执行刷新操作，然后在迟些时候继续。
    下面显示了 ChannelOutboundHandler 的方法（继承自 ChannelHandler 未列出来）

    类型	描述
    bind	Invoked on request to bind the Channel to a local address
    connect	Invoked on request to connect the Channel to the remote peer
    disconnect	Invoked on request to disconnect the Channel from the remote peer
    close	Invoked on request to close the Channel
    deregister	Invoked on request to deregister the Channel from its EventLoop
    read	Invoked on request to read more data from the Channel
    flush	Invoked on request to flush queued data to the remote peer through the Channel
    write	Invoked on request to write data through the Channel to the remote peer

    Level Description	DISABLED
    Disables	Leak detection completely. While this even eliminates the 1 % overhead you should only do this after extensive testing.
    SIMPLE	Tells if a leak was found or not. Again uses the sampling rate of 1%, the default level and a good fit for most cases.
    ADVANCED	Tells if a leak was found and where the message was accessed, using the sampling rate of 1%.
    PARANOID	Same as level ADVANCED with the main difference that every access is sampled. This it has a massive impact on performance. Use this only in the debugging phase.
 */
public class ChannelOutboundHandler {
    public static void main(String[] args) {

        ChannelPipeline pipeline = null; // get reference to pipeline;
        DiscardHandler firstHandler = new DiscardHandler(); //1
        pipeline.addLast("handler1", firstHandler); //2
        pipeline.addFirst("handler2", new DiscardHandler()); //3
        pipeline.addLast("handler3", new DiscardHandler()); //4

        pipeline.remove("handler3"); //5
        pipeline.remove(firstHandler); //6

        pipeline.replace("handler2", "handler4", new DiscardHandler()); //6

    }
}

