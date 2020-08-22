//package com.netty.c15Senior.decoder;
//
//
///**
//    该类是负责编码 MemachedRequest 为一系列字节
//    转换的 key 和实际请求的 body 到字节数组
//    计算 body 大小
//    写幻数到 ByteBuf 字节
//    写 opCode 作为字节
//    写 key 长度z作为 short
//    编写额外的长度作为字节
//    写数据类型,这总是0,因为目前不是在 Memcached,但可用于使用 后来的版本
//    为保留字节写为 short ,后面的 Memcached 版本可能使用
//    写 body 的大小作为 long
//    写 opaque 作为 int
//    写 cas 作为 long。这个是头文件的最后部分，在 body 的开始
//    编写额外的 flag 和到期时间为 int
//    写 key
//    这个请求完成后 写 body。
//    总结，编码器 使用 Netty 的 ByteBuf 处理请求，编码 MemcachedRequest 成一套正确排序的字节。详细步骤为：
//
//    写幻数字节。
//    写 opcode 字节。
//    写 key 长度(2字节)。
//    写额外的长度(1字节)。
//    写数据类型(1字节)。
//    为保留字节写 null 字节(2字节)。
//    写 body 长度(4字节- 32位整数)。
//    写 opaque(4个字节,一个32位整数在响应中返回)。
//    写 CAS(8个字节)。
//    写 额外的(flag 和 到期,4字节)= 8个字节
//    写 key
//    写 值
//    无论你放入什么到输出缓冲区( 调用 ByteBuf) Netty 的将向服务器发送被写入请求。下一节将展示如何进行反向通过解码器工作。
// */
//public class MemcachedResponse {
//    private final byte magic;
//    private final byte opCode;
//    private byte dataType;
//    private final short status;
//    private final int id;
//    private final long cas;
//    private final int flags;
//    private final int expires;
//    private final String key;
//    private final String data;
//
//    public MemcachedResponse(byte magic, byte opCode, short status, int id, long cas, int flags, int expires, String key, String data, int expires1) {
//        this.expires = expires1;
//    }
//
//    public MemcachedResponse(byte magic, byte opCode,
//                             byte dataType,
//                             short status,
//                             int id, long cas,
//                             int flags, int expires, String key, String data) {
//        this.magic = magic;
//        this.opCode = opCode;
//        this.dataType = dataType;
//        this.status = status;
//        this.id = id;
//        this.cas = cas;
//        this.flags = flags;
//        this.expires = expires;
//        this.key = key;
//        this.data = data;
//    }
//
//    public byte magic() { //2
//        return magic;
//    }
//    public byte opCode() { //3
//        return opCode;
//    }
//    public byte dataType() { //4
//        return dataType;
//    }
//    public short status() {  //5
//        return status;
//    }
//    public int id() {  //6
//        return id;
//    }
//    public long cas() {  //7
//        return cas;
//    }
//    public int flags() {  //8
//        return flags;
//    }
//    public int expires() { //9
//        return expires;
//    }
//    public String key() {  //10
//        return key;
//    }
//    public String data() {  //11
//        return data;
//    }
//}
//
