package com.netty.c05ByteBufFin;


// Netty字节级别的操作

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Random;

/**
    除了基本的读写操作， ByteBuf 还提供了它所包含的数据的修改方法。
    随机访问索引
    ByteBuf 使用zero-based 的 indexing(从0开始的索引)，
    第一个字节的索引是 0，最后一个字节的索引是 ByteBuf 的 capacity - 1
 */
public class ByteBitOperation3BitLevel {
    public static void main(String[] args) {

        // 随机访问索引
        ByteBuf byteBuf = Unpooled.directBuffer();
        for (int i=0;i<byteBuf.capacity();i++) {
            System.out.print((char)byteBuf.getByte(i));
        }

        ByteBuf byteBuf6 = byteBuf.copy();
        byteBuf.maxWritableBytes();
        byteBuf.maxCapacity();

        System.out.println();
        if(byteBuf.hasMemoryAddress()) {
            System.out.println("hasMemoryAddress: "+byteBuf.memoryAddress());
        } else {
            System.out.println("not have any hasMemoryAddress. ");
        }


        // write date
        ByteBuf byteBuf7 = Unpooled.directBuffer(128);
        Random random = new Random(128);
        while (byteBuf7.writableBytes()>=4) {
            byteBuf7.writeInt(random.nextInt());
        }
        // clear() 比 discardReadBytes() 更低成本，因为他只是重置了索引，而没有内存拷贝
        byteBuf7.clear();
        byteBuf7.discardReadBytes();


        // index management
        ByteBuf byteBuf8 = Unpooled.directBuffer(1024);
        byteBuf8.writeBytes("BYTE-CONTENT".getBytes());
        int len = byteBuf8.readableBytes();
        byte[] bytes = new byte[len];
        byteBuf8.getBytes(byteBuf8.readerIndex(),bytes);
        System.out.println("new bytes content: "+new String(bytes));

        byteBuf8.clear();
        int lennew = byteBuf8.readableBytes();
        System.out.println(byteBuf8.readableBytes());
        System.out.println(byteBuf8.writerIndex());
        System.out.println(byteBuf8.readerIndex());
        System.out.println(byteBuf8);

        ByteBuf byteBuf8mark = byteBuf8.markReaderIndex();
        ByteBuf byteBuf8new = byteBuf8.readerIndex(0);
        ByteBuf byteBuf8M = byteBuf8.resetReaderIndex();
        ByteBuf byteBuf8N = byteBuf8.resetWriterIndex();
        ByteBuf byte8copy = byteBuf8.copy();
        // as read only
        ByteBuf byte8Cp = byteBuf8.asReadOnly();
        ByteBuf byteBn = byteBuf8.discardReadBytes();
        ByteBuf byte8o = byteBuf8.discardSomeReadBytes();
        // copy
        ByteBuf byte8p = byteBuf8.duplicate();
        ByteBuf byte8u = byteBuf8.ensureWritable(1024);
        ByteBuf byte8v = byteBuf8.markWriterIndex();
        ByteBuf byte8z = byteBuf8.discardReadBytes();
        ByteBuf byte8q = byteBuf8.discardSomeReadBytes();
        ByteBuf byte8w = byteBuf8.ensureWritable(8);
        ByteBuf byte8c = byteBuf8.markWriterIndex();
        ByteBuffer[] byte8s = byteBuf8.nioBuffers();
        byteBuf8.isDirect();
        byteBuf8.isReadable();
        byteBuf8.isWritable();
        ByteBuf byteWriteBuf = byteBuf8.markWriterIndex();


        Buffer buffernio = byteBuf8.nioBuffer();
        byteBuf8.writeBoolean(true);
        byteBuf8.writeInt(1);


        // forEachByte（ByteBufProcessor.FIND_NUL）


        /**
         衍生的缓冲区
         “衍生的缓冲区”是代表一个专门的展示 ByteBuf 内容的“视图”。这种视图是由 duplicate(), slice(), slice(int, int),readOnly(), 和 order(ByteOrder) 方法创建的。所有这些都返回一个新的 ByteBuf 实例包括它自己的 reader, writer 和标记索引。然而，内部数据存储共享就像在一个 NIO 的 ByteBuffer。这使得衍生的缓冲区创建、修改其 内容，以及修改其“源”实例更廉价。

         ByteBuf 拷贝
         如果需要已有的缓冲区的全新副本，使用 copy() 或者 copy(int, int)。不同于派生缓冲区，这个调用返回的 ByteBuf 有数据的独立副本。
         */


    }
}

