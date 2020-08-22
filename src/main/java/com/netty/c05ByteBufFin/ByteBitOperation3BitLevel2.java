package com.netty.c05ByteBufFin;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;

import java.nio.Buffer;
import java.nio.charset.Charset;

public class ByteBitOperation3BitLevel2 {
    public static void main(String[] args) {

        // 1.create
        ByteBuf byteBuf = Unpooled.directBuffer(128);
        // 2.write
        byteBuf.writeBytes("CONTENT-BUFFER".getBytes());
        // 3.
        int len = byteBuf.readableBytes();
        byte[] bytes = new byte[len];
        byteBuf.getBytes(byteBuf.readerIndex(),bytes);
        // 4.
        System.out.print(new String(bytes));

        System.out.println("get readerIndex: "+byteBuf.readerIndex());
        System.out.println("set readerIndex: "+byteBuf.readerIndex(2));
        System.out.println("get writerIndex: "+byteBuf.writerIndex());
//        System.out.println("set writerIndex: "+byteBuf.writerIndex(1));

        ByteBuf Bytebuf2 = byteBuf.discardReadBytes();
        System.out.println(byteBuf.writeInt(10));
        byteBuf.writeBoolean(true);
        // convert to nio buffer
        Buffer buffer = byteBuf.nioBuffer();
        Buffer buffers = byteBuf.nioBuffer(0,5);
        byteBuf.nioBufferCount();
        byteBuf.discardReadBytes();
        byteBuf.ensureWritable(0);
        byteBuf.isReadOnly();
//        byteBuf.arrayOffset();
        byteBuf.markWriterIndex();
        byteBuf.hasMemoryAddress();
        byteBuf.readDouble();
        byteBuf.maxCapacity();
        byteBuf.retain();
        // set to 0
        byteBuf.resetWriterIndex();
        byteBuf.readUnsignedShort();

        System.out.println(byteBuf.readerIndex());
        System.out.println(byteBuf.readerIndex());
        System.out.println(byteBuf.readerIndex());
        System.out.println(byteBuf.readerIndex());

        System.out.println(byteBuf.readerIndex());
        System.out.println(byteBuf.readerIndex());
        System.out.println(byteBuf.readerIndex());
        System.out.println(byteBuf.readerIndex());
        System.out.println(byteBuf.readerIndex());

        System.out.println(byteBuf.readerIndex());
        System.out.println(byteBuf.readerIndex());
        System.out.println(byteBuf.readerIndex());
        System.out.println(byteBuf.readerIndex());
        System.out.println(byteBuf.readerIndex());


        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action Rocks!",utf8);

        ByteBuf sliced = buf.slice(0,14);
        System.out.println(sliced.toString(utf8));

        buf.setByte(0, (byte)'J');
        assert buf.getByte(0) == sliced.getByte(0);

        /**
            1.创建一个 ByteBuf 保存特定字节串。
            2.创建从索引 0 开始，并在 14 结束的 ByteBuf 的新 slice。
            3.打印 Netty in Action
            4.更新索引 0 的字节。
            5.断言成功，因为数据是共享的，并以一个地方所做的修改将在其他地方可见。

            1.创建一个 ByteBuf 保存特定字节串。
            2.创建从索引0开始和 14 结束 的 ByteBuf 的段的拷贝。
            3.打印 Netty in Action
            4.更新索引 0 的字节。
            5.断言成功，因为数据不是共享的，并以一个地方所做的修改将不影响其他。
            代码几乎是相同的，但所 衍生的 ByteBuf 效果是不同的。因此，使用一个 slice 可以尽可能避免复制内存。


            读/写操作
            读/写操作主要由2类：

            gget()/set() 操作从给定的索引开始，保持不变
            read()/write() 操作从给定的索引开始，与字节访问的数量来适用，递增当前的写索引或读索引
         */
        byteBuf.getByte(0);
        byteBuf.getByte('c');

        byteBuf.setBoolean(0,true);
        byteBuf.setByte(1,Byte.valueOf("120"));
        byteBuf.setMedium(2,6);
        byteBuf.setInt(3,10);
        byteBuf.setLong(1,new Long(190));
        byteBuf.setShort(5,Short.valueOf("120"));

        byteBuf.resetWriterIndex();
        byteBuf.resetReaderIndex();
        byteBuf.writeBytes("NEW-WRITE-BYTES".getBytes());
        int lennew = byteBuf.readableBytes();
        byte[] bytearr = new byte[lennew];
        System.out.println("readableBytes: "+byteBuf.readableBytes());
        byteBuf.resetReaderIndex();
        byteBuf.resetWriterIndex();
        byteBuf.markWriterIndex();

        byteBuf.getBytes(byteBuf.readerIndex(),bytearr);
        System.out.println(new String(bytearr));


        // clear byteBuf
        byteBuf.discardReadBytes();
        System.out.println(byteBuf.readableBytes());

        byteBuf.touch();
        byteBuf.alloc();
        byteBuf.refCnt();
        byteBuf.release();

        byteBuf.writeBytes("BTTE-CONTENT".getBytes());
        System.out.println(byteBuf.writableBytes());
        System.out.println(byteBuf.capacity());

        // no array operation
//        byteBuf.arrayOffset();
        byteBuf.unwrap();
        byteBuf.readLongLE();


        /**
            Table 5.1 get() operations

            方法名称	描述
            getBoolean(int)	返回当前索引的 Boolean 值
            getByte(int) getUnsignedByte(int)	返回当前索引的(无符号)字节
            getMedium(int) getUnsignedMedium(int)	返回当前索引的 (无符号) 24-bit 中间值
            getInt(int) getUnsignedInt(int)	返回当前索引的(无符号) 整型
            getLong(int) getUnsignedLong(int)	返回当前索引的 (无符号) Long 型
            getShort(int) getUnsignedShort(int)	返回当前索引的 (无符号) Short 型
            getBytes(int, ...)	字节
            常见 set() 操作如下

            Table 5.2 set() operations

            方法名称	描述
            setBoolean(int, boolean)	在指定的索引位置设置 Boolean 值
            setByte(int, int)	在指定的索引位置设置 byte 值
            setMedium(int, int)	在指定的索引位置设置 24-bit 中间 值
            setInt(int, int)	在指定的索引位置设置 int 值
            setLong(int, long)	在指定的索引位置设置 long 值
            setShort(int, int)	在指定的索引位置设置 short 值
         */


        Charset utf8ii = Charset.forName("UTF-8");
        ByteBuf bbuf = Unpooled.copiedBuffer("Netty in Action rocks!",utf8);
        System.out.println((char)buf.getByte(0));

        int readerIndex = buf.readerIndex();
        buf.readerIndex(2);
        int writerIndex = buf.writerIndex();
        buf.writerIndex(6);

        buf.setByte(0, (byte)'B');
        System.out.println((char)buf.getByte(1));

        assert readerIndex == buf.readerIndex();
        assert writerIndex == buf.writerIndex();

        System.out.println("Operation finish...");


        int aa = 100;
        int bb = 102;
        assert aa == 11;

        System.out.println(buf.readBoolean());
        System.out.println(buf.readByte());
        System.out.println(buf.readUnsignedByte());
//        System.out.println(buf.readMedium());

//        System.out.println(buf.readUnsignedMedium());
//        System.out.println(buf.readUnsignedMediumLE());
//        System.out.println(buf.readUnsignedShort());
//        System.out.println(buf.readUnsignedShortLE());

//        System.out.println();
//        System.out.println(buf.readUnsignedMediumLE());
//        System.out.println(buf.readUnsignedShort());
//        System.out.println(buf.readUnsignedShortLE());

        byteBuf.isReadOnly();
        byteBuf.isWritable();
        byteBuf.readableBytes();
        byteBuf.writableBytes();
        byteBuf.capacity();
        byteBuf.maxCapacity();

        if(byteBuf.hasArray()) {
            System.out.println(new String(byteBuf.array()));
        }




    }
}

