package com.netty.c15Senior.decoder;



/**
    一个 Opcode 告诉 Memcached 要执行哪些操作。每个操作都由一个字节表示。
    同样的,当 Memcached 响应一个请求,响应头中包含两个字节代表响应状态。
    状态和 Opcode 类表示这些 Memcached 的构造。
    这些操作码可以使用当你构建一个新的 MemcachedRequest 指定哪个行动应该由它引发的。
 */
public class Status {
    public static final short NO_ERROR = 0x0000;
    public static final short KEY_NOT_FOUND = 0x0001;
    public static final short KEY_EXISTS = 0x0002;
    public static final short VALUE_TOO_LARGE = 0x0003;
    public static final short INVALID_ARGUMENTS = 0x0004;
    public static final short ITEM_NOT_STORED = 0x0005;
    public static final short INC_DEC_NON_NUM_VAL = 0x0006;
}
// public
class Opcode {
    public static final byte GET = 0x00;
    public static final byte SET = 0x01;
    public static final byte DELETE = 0x04;
}

