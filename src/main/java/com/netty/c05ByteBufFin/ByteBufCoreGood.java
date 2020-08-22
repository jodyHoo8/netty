package com.netty.c05ByteBufFin;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.ByteBuffer;
import java.util.Iterator;

public class ByteBufCoreGood {
    public static void main(String[] args) {

        // unpooled 非池化
        ByteBuf byteBuf = Unpooled.buffer(16);

        // 读 - 写 指针
        // 初始化0

        // head buffer - 堆缓冲区
        ByteBuf heapBuf = Unpooled.buffer(24);
        heapBuf.writeBytes("HELLO-HEAP-BUFFER".getBytes());
        System.out.println(new String(heapBuf.array()));
        if (heapBuf.hasArray()) {
            byte[] array = heapBuf.array();
            int offset = heapBuf.arrayOffset() + heapBuf.readerIndex();
            int length = heapBuf.readableBytes();
            System.out.println("offset: " + offset + " length: " + length);
            System.out.println("DirectBuf not support array, content: " + new String(array));
        }
        /**
         访问非堆缓冲区 ByteBuf 的数组会导致UnsupportedOperationException，
         可以使用 ByteBuf.hasArray()来检查是否支持访问数组。
         这个用法与 JDK 的 ByteBuffer 类似
         */

        // direct buffer - 直接缓冲区
        ByteBuf directBuf = Unpooled.directBuffer(32);
        directBuf.writeBytes("HI-DIRECT-BUFFER".getBytes());
        // 不能直接转 array
        if (directBuf.hasArray()) {
            System.out.println(new String(directBuf.array()));
        } else if (!directBuf.hasArray()) {
            int length = directBuf.readableBytes();
            byte[] array = new byte[length];
            directBuf.getBytes(directBuf.readerIndex(), array);
            System.out.println("DirectBuf not support array, content: " + new String(array));
        }

        /**
         直接缓冲区
         另一个ByteBuf模式 对象索引内存分配发生在堆.
         并非总数如此, 在JDK 1.4 被引入 NIO 的ByteBuffer类
         允许JVM通过本地方法调用分配内存

         通过免去中间交换的内存拷贝，提升IO处理速度
         直接缓冲区内容可以驻留在垃圾会输扫描堆区以外

         NIO的ByteBuffer允许JVM通过
         --  本地方法调用
         分配内存

         通过免去中间交互的 -- 内存拷贝
         提升IO处理速度，
         直接缓冲区内容可以驻留在垃圾回收扫描区外

         DirectBuffer 在 -XX:MaxDirectMemorySize=xxM大小限制下,
         使用Heap之外的内存
         GC对此 无能为力 规避了在高负载下
         频繁的GC过程对应用线程的
         中断影响

         直接缓冲区 对于通过socket实现数据传输的应用来说
         是一种非常理想的方式
         数据存放在堆中分配的缓冲区
         实际上
         通过socket发送数据之前，JVM需要先将数据复制到
         -- 直接缓冲区

         直接缓冲区确定 内存空间分配释放比 堆缓冲区更复杂
         缺点 数据传递给 遗留代码处理
         因为数据不是在堆上 可能不得不作一个
         -- 副本
         */

        // composite buffer - 复合缓冲区
        CompositeByteBuf compositeByteBuf =
                Unpooled.compositeBuffer(128);
        compositeByteBuf.addComponent(byteBuf);
        compositeByteBuf.addComponent(directBuf);
        if (compositeByteBuf.hasArray()) {
            System.out.println("compositeBuf has array.");
        } else {
            System.out.println("compositeBuf not has array.");
        }

        /**
             1.检查 ByteBuf 是否有支持数组。
             2.如果有的话，得到引用数组。
             3.计算第一字节的偏移量。
             4.获取可读的字节数。
             5.使用数组，偏移量和长度作为调用方法的参数。
         */


        // NIO 读写 array
        // 比较慢
        ByteBuffer header = ByteBuffer.wrap("Header-Buffer ".getBytes());
        ByteBuffer body = ByteBuffer.wrap("Body-Buffer ".getBytes());
        ByteBuffer[] message = { header, body };

        ByteBuffer message2 =
                ByteBuffer.allocate(header.remaining()+body.remaining());
        message2.put(header);
        message2.put(body);
        message2.flip();

        System.out.println(new String(message2.array()));


        CompositeByteBuf messageBuf = Unpooled.compositeBuffer(1024);
        ByteBuf headerBuf = Unpooled.wrappedBuffer("header-buffer".getBytes());
        ByteBuf bodyBuf = Unpooled.wrappedBuffer("body-buffer".getBytes());
        messageBuf.addComponents(headerBuf,bodyBuf);
        messageBuf.removeComponent(0);

        for (int i=0;i<messageBuf.numComponents();i++) {
            System.out.println(messageBuf.component(i).toString());
            System.out.println(new String(messageBuf.component(i).array()));
        }


        CompositeByteBuf compBuf = Unpooled.compositeBuffer(32);

        // 1.添加DirectBuf 作为component
        //   不能使用readBytes读出来
        ByteBuf directBuf1 = Unpooled.directBuffer(48);
        directBuf1.writeBytes("WRITECONTENT".getBytes());
        ByteBuf directBuf2 = Unpooled.directBuffer(48);
        directBuf2.writeBytes("DIRECT-BUFFER2".getBytes());
        compBuf.addComponent(directBuf1);
        compBuf.addComponent(directBuf2);

        // 2.直接添加bytes内容
        compBuf.writeBytes("CONTENT".getBytes());

        // 3.转成数组
        int length = compBuf.readableBytes();    //1
        System.out.println("length: "+length);
        byte[] array = new byte[length];        //2
        // 这里getBytes读取的是 直接通过 weiteBytes写进byte[] 数组里的内容
        compBuf.getBytes(compBuf.readerIndex(), array);    //3
        System.out.println(new String(array));
//        handleArray(array, 0, length);    //4

        Iterator<ByteBuf> byteIter = compBuf.iterator();
        while(byteIter.hasNext()) {
            ByteBuf byteB = byteIter.next();
            if (byteB.hasArray()) {
                System.out.println("heapBuf: "+new String(byteB.array()));
            } else {
                int len = byteB.readableBytes();
                byte[] bytes = new byte[len];
                byteB.getBytes(byteB.readerIndex(), bytes);
                System.out.println("directBuf: "+new String(bytes));
            }
        }

    }
}

