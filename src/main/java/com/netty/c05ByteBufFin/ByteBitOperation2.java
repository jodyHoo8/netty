package com.netty.c05ByteBufFin;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ByteBitOperation2 {
    public static void main(String[] args) {

        ByteBuf byteBuf = Unpooled.directBuffer(128);
        byteBuf.writeBytes("BYTECONTENT".getBytes());
        int len = byteBuf.readableBytes();
        byte[] bytes = new byte[len];
        // reader index
        byteBuf.getBytes(byteBuf.readerIndex(),bytes);

        System.out.println(new String(bytes));
        // 读 index - 写 index
        System.out.println(byteBuf.readerIndex());
        System.out.println(byteBuf.writerIndex());
        System.out.println(byteBuf.readableBytes());

        // direct buffer write and read
        ByteBuf byteBuf3 = Unpooled.directBuffer(128);
        // write
        byteBuf3.writeBytes("WRITEBYTES-CONTENT".getBytes());
        // read
        int len3 = byteBuf3.readableBytes();
        byte[] bytes3 = new byte[len3];
        byteBuf3.getBytes(byteBuf3.readerIndex(),bytes3);
        System.out.println(new String(bytes3));
        System.out.println("readerIndex: "+byteBuf3.readerIndex());
        System.out.println("writerIndex: "+byteBuf3.writerIndex());

        // random access index
        for (int i=0;i<byteBuf3.capacity();i++) {
            byte b = byteBuf3.getByte(i);
            System.out.print((char)b);
        }


        // readerIndex
        // writerIndex-

        /**
            1.字节，可以被丢弃，因为它们已经被读
            2.还没有被读的字节是：“readable bytes（可读字节）”
            3.空间可加入多个字节的是：“writeable bytes（写字节）”
         */
        System.out.println();
        System.out.println("readerIndex: "+byteBuf3.readerIndex());
        System.out.println("writerIndex: "+byteBuf3.writerIndex());
        System.out.println("capacity: " +byteBuf3.capacity());
        System.out.println("maxCapacity: "+byteBuf3.maxCapacity());
        System.out.println("maxWritableBytes: "+byteBuf3.maxWritableBytes());

        System.out.println();
        // 设置 readerIndex
        ByteBuf byteBuf4 = byteBuf3.readerIndex(10);
        System.out.println("readerIndex: "+byteBuf4.readerIndex());
        System.out.println("writerIndex: "+byteBuf4.writerIndex());
        System.out.println("capacity: " +byteBuf4.capacity());

        // read bytes from new readerIndex
        int len4 = byteBuf4.readableBytes();
        byte[] bytes4 = new byte[len4];
        byteBuf4.getBytes(byteBuf4.readerIndex(),bytes4);
        System.out.println(new String(bytes4));


        ByteBuf byteBuf5 = byteBuf4.markReaderIndex();
        ByteBuf byteBuf6 = byteBuf4.resetReaderIndex();


        byteBuf4.resetWriterIndex();
        System.out.println(byteBuf4.writerIndex());
//        byteBuf4.writeBytes("CORE".getBytes());

//        byteBuf4.readerIndex(10);
        byteBuf4.readerIndex();
        System.out.println(byteBuf4.readableBytes());

        int len4b = byteBuf4.readableBytes();
        System.out.println(byteBuf4.readableBytes());
        byte[] bytes5 = new byte[20];
        byteBuf4.getBytes(0,bytes5);
        System.out.println(new String(bytes5));


        /**
            1.字节，可以被丢弃，因为它们已经被读
            2.还没有被读的字节是：“readable bytes（可读字节）”
            3.空间可加入多个字节的是：“writeable bytes（写字节）”
         */


    }
}

