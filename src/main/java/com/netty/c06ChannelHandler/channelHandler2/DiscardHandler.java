package com.netty.c06ChannelHandler.channelHandler2;


import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;

/**
    修改 ChannelPipeline
    ChannelHandler 可以实时修改 ChannelPipeline 的布局，通过添加、移除、替换其他 ChannelHandler（也可以从 ChannelPipeline 移除 ChannelHandler 自身）。这个 是 ChannelHandler 重要的功能之一。
 */
@ChannelHandler.Sharable
class DiscardOutboundHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx,
                      Object msg, ChannelPromise promise) {
        ReferenceCountUtil.release(msg);  //2
        promise.setSuccess();    //3

    }
}


