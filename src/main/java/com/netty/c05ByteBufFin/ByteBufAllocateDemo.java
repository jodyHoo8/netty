package com.netty.c05ByteBufFin;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AsciiString;
import io.netty.util.HashingStrategy;

import javax.imageio.stream.IIOByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Collections;

public class ByteBufAllocateDemo {
    public static void main(String[] args) {

        // 1.ByteBufAllocator
        ByteBuf byteBuf = Unpooled.directBuffer(128);

        ByteBuf heapBuf = Unpooled.buffer();
        ByteBuf heapBuf2 = Unpooled.buffer(128);
        ByteBuf heapBuf3 = Unpooled.buffer(10,128);

        // 2.1.generate bytebuf allocator
        ByteBufAllocator.DEFAULT.heapBuffer();

        // heapBuffer
        byteBuf.alloc().heapBuffer();
        // directBuffer
        byteBuf.alloc().directBuffer();

        ByteBufAllocator.DEFAULT.directBuffer(128);
        ByteBufAllocator.DEFAULT.directBuffer(10,128);

        ByteBufAllocator.DEFAULT.compositeBuffer();
        ByteBufAllocator.DEFAULT.compositeBuffer(128);
        ByteBufAllocator.DEFAULT.compositeHeapBuffer();
        ByteBufAllocator.DEFAULT.compositeHeapBuffer(128);
        ByteBufAllocator.DEFAULT.compositeDirectBuffer();
        ByteBufAllocator.DEFAULT.compositeDirectBuffer(128);
        ByteBufAllocator.DEFAULT.ioBuffer();
        ByteBuf iioByteBuf = ByteBufAllocator.DEFAULT.ioBuffer(128);

        ChannelHandlerContext ctx = null;

        /**
            Channel
            ChannelHandler
            ChannePipeline
            ChannelHandlerContext

            研究Netty中的ChannelHandler 和 ChannelPipeline
         */

        // 2.Unpooed 非池化创建 ByteBuf

        ByteBuf byteBuf2 = Unpooled.buffer(128);
        ByteBuf byteBuf3 = Unpooled.buffer();

        Unpooled.directBuffer(128);
        Unpooled.directBuffer();
        Unpooled.directBuffer(16,128);

        Unpooled.compositeBuffer(128);
        Unpooled.compositeBuffer();

        byte[] bytes = new byte[10];
        Unpooled.wrappedBuffer(bytes);
        byte[] bytearr = new byte[16];
        ByteBuf byteBuf10 = Unpooled.buffer();
        ByteBuf byteBuf11 = Unpooled.wrappedBuffer(byteBuf10);

        byte[] byteBufArr = new byte[16];
        Unpooled.wrappedBuffer(10,byteBufArr);

        ByteBuf byteBuf12 = Unpooled.buffer();
        ByteBuf byteBuf14 = Unpooled.copiedBuffer(byteBuf12);


        // 3.ByteBufUtil
        CharBuffer charBuffer = CharBuffer.allocate(16);
        ByteBuf desBuf = ByteBufUtil.encodeString(ByteBufAllocator.DEFAULT, charBuffer, Charset.forName("UTF-8"));
        ByteBufUtil.copy(AsciiString.EMPTY_STRING,desBuf);
        ByteBuf srcBuf = ByteBufUtil.writeAscii(ByteBufAllocator.DEFAULT,"SENTENCE-UTIL");
//        ByteBufUtil.copy(AsciiString.cached("UTF-8"),10,srcBuf,2,16);

        // int srcIdx, ByteBuf dst, int dstIdx, int length
        HashingStrategy<CharSequence> cs = AsciiString.CASE_INSENSITIVE_HASHER;

        char b2c = AsciiString.b2c(Byte.valueOf("127"));
        byte c2b = AsciiString.c2b(Character.valueOf('c'));

        AsciiString.cached("CACHED");
        AsciiString.contains("aa","cc");
        int have = AsciiString.indexOf("CHARACTER",'C',0);

        AsciiString.isUpperCase('U');
        AsciiString.isUpperCase(Byte.MAX_VALUE);
        AsciiString.containsIgnoreCase("Character","aaa");
        AsciiString.containsAllContentEqualsIgnoreCase(Collections.emptyList(),Collections.emptyList());
        AsciiString.of("Sequence");

        // AsciiString.regionMatches(true,1,"Character",2,10);
        int indexnotf = AsciiString.INDEX_NOT_FOUND;
        // CharSequence cs, boolean ignoreCase, int csStart, CharSequence string, int start, int length

        AsciiString.regionMatchesAscii("CharSequence",true,2,"CharSequence",2,10);


        // 4.release buffer
        ByteBuf byteBuf16 = ByteBufAllocator.DEFAULT.heapBuffer();
        boolean released = byteBuf16.release();
        System.out.println(released);


    }
}

