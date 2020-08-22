package com.netty.c06NettyReference;


import io.netty.buffer.*;
import io.netty.channel.ChannelHandler.Sharable;

@Sharable
public class NettyReferenceDemo {
    public static void main(String[] args) throws Exception {

//        EventLoopGroup eventLoopGroup = new EventLoopGroup();
        ByteBuf fyteBuf = PooledByteBufAllocator.DEFAULT.buffer();
        CompositeByteBuf compositeByteBuf = UnpooledByteBufAllocator.DEFAULT.compositeDirectBuffer();

        UnpooledDirectByteBuf unpooledDirectByteBuf;

        UnpooledByteBufAllocator.DEFAULT.compositeHeapBuffer();
        UnpooledByteBufAllocator.DEFAULT.compositeHeapBuffer(128);
        UnpooledByteBufAllocator.DEFAULT.compositeDirectBuffer(128);
        UnpooledByteBufAllocator.DEFAULT.compositeDirectBuffer();

    }
}

