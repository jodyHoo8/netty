package com.netty.c11Instance;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
    继承 ByteToMessageDecoder 用于解码入站字节到消息
    指定最大需要的帧产生的体积
    如果帧太大就丢弃并抛出一个 TooLongFrameException 异常
    同时从 ByteBuf 读到新帧
    添加帧到解码消息 List
 */
public class FrameChunkDecoder extends ByteToMessageDecoder {  //1
    private final int maxFrameSize;
    public FrameChunkDecoder(int maxFrameSize) {
        this.maxFrameSize = maxFrameSize;
    }
    @Override
    protected void decode(ChannelHandlerContext ctx,
                          ByteBuf in,
                          List<Object> out) throws Exception {
        int readableBytes = in.readableBytes();  //2
        if (readableBytes > maxFrameSize)  {
            // discard the bytes   //3
            in.clear();
            throw new TooLongFrameException();
        }
        ByteBuf buf = in.readBytes(readableBytes); //4
        out.add(buf);  //5
    }
}

