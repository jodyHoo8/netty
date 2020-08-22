package com.netty.c11Instance;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
    Netty如何测试 ChannelHandler
    本节的内容是介绍Netty中要如何通过 EmbeddedChannel 对 ChannelHandler 进行测试。

    测试入站消息
    我们先来编写一个简单的 ByteToMessageDecoder 实现，在有足够的数据可以读取的情况下将产生固定大小的包，如果没有足够的数据可以读取，则会等待下一个数据块并再次检查是否可以产生一个完整包。

    如图所示，它可能会占用一个以上的“event”以获取足够的字节产生一个数据包，并将它传递到 ChannelPipeline 中的下一个 ChannelHandler，


    继承 ByteToMessageDecoder 用来处理入站的字节并将他们解码为消息
    指定产出的帧的长度
    检查是否有足够的字节用于读到下个帧
    从 ByteBuf 读取新帧
    添加帧到解码好的消息 List


    测试增加 @Test 注解
    新建 ByteBuf 并用字节填充它
    新增 EmbeddedChannel 并添加 FixedLengthFrameDecoder 用于测试
    写数据到 EmbeddedChannel
    标记 channel 已经完成
    读产生的消息并且校验


    Testing outbound messages
    测试的处理出站消息类似于我们刚才看到的一切。这个例子将使用的实现MessageToMessageEncoder:AbsIntegerEncoder。

    当收到 flush() 它将从 ByteBuf 读取4字节整数并给每个执行Math.abs()。
    每个整数接着写入 ChannelHandlerPipeline
 */
public class FixedLengthFrameDecoder extends ByteToMessageDecoder { //1
    private final int frameLength;
    public FixedLengthFrameDecoder(int frameLength) { //2
        if (frameLength <= 0) {
            throw new IllegalArgumentException(
                    "frameLength must be a positive integer: " + frameLength);
        }
        this.frameLength = frameLength;
    }
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() >= frameLength) { //3
            ByteBuf buf = in.readBytes(frameLength);//4
            out.add(buf); //5
        }
    }
}

