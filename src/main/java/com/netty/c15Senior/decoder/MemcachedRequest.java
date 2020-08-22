package com.netty.c15Senior.decoder;


import java.util.Random;

/**
    这个类将会发送请求到 Memcached server
    幻数，它可以用来标记文件或者协议的格式
    opCode,反应了响应的操作已经创建了
    执行操作的 key
    使用的额外的 flag
    表明到期时间
    body
    请求的 id。这个id将在响应中回显。
    compare-and-check 的值
    如果有额外的使用，将返回 true
 */
public class MemcachedRequest {
    private static final Random rand = new Random();
    private final int magic = 0x80;//fixed so hard coded
    private final byte opCode; //the operation e.g. set or get
    private final String key; //the key to delete, get or set
    private final int flags = 0xdeadbeef; //random
    private final int expires; //0 = item never expires
    private final String body; //if opCode is set, the value
    private final int id = rand.nextInt(); //Opaque
    private final long cas = 0; //data version check...not used
//    private final boolean hasExtras; //not all ops have extras
    public MemcachedRequest(byte opcode, String key, String value) {
        this.opCode = opcode;
        this.key = key;
        this.body = value == null ? "" : value;
        this.expires = 0;
        //only set command has extras in our example
//        hasExtras = opcode == Opcode.SET;
    }
    public MemcachedRequest(byte opCode, String key) {
        this(opCode, key, null);
    }
    public int magic() { //2
        return magic;
    }
    public int opCode() {  //3
        return opCode;
    }
    public String key() {  //4
        return key;
    }
    public int flags() {  //5
        return flags;
    }
    public int expires() {  //6
        return expires;
    }
    public String body() {  //7
        return body;
    }
    public int id() {  //8
        return id;
    }
    public long cas() {  //9
        return cas;
    }
    public boolean hasExtras() {  //10
        return true;
        //hasExtras;
    }
}

