package com.netty.c15Senior.decoder;


/**
    Netty 编码器和解码器
    Netty是一个复杂和先进的框架。如果我们请求了某些有设置 key 的给定值，那么 Request 类的一个实例就被建立来代表这个请求。但是 Request 对象转成 Memcached 所期望的过程，Netty并不知道。Memcached 所期望的是字节序列，看，不管使用的协议是什么，数据在网络上永远是按字节序列传输的。

    要想将 Request 对象转为 Memcached 所需的字节序列，Netty 需要使用 MemcachedRequest 来编码成另外一种格式。可以从对象转为字节，也可以从对象转为对象，又或者从对象转为字符串等。有关编码器的内容详见第七章。

    MessageToByteEncoder 是 Netty 提供的一个抽象类。这个抽象类提供了一个抽象方法，可以将一条消息（在本例中我们的 MemcachedRequest 对象）转为字节。你显示什么信息实现可以通过使用 Java 泛型处理；例如，MessageToByteEncoder 说这个编码器要编码的对象类型是 MemcachedRequest。

    MessageToByteEncoder 和 Java 泛型

    使用 MessageToByteEncoder 可以绑定特定的参数类型。如果你有多个不同的消息类型，在相同的编码器里，也可以使用MessageToByteEncoder，注意检查消息的类型即可

    这同样也适用于解码器，除了解码器将一系列字节转换回一个对象。 这个 Netty 的提供了 ByteToMessageDecoder 类,而不是提供一个编码方法用来实现解码。在接下来的两个部分你看看如何实现一个 Memcached 解码器和编码器。在你做之前,应该意识到在使用 Netty 时，你不总是需要自己提供编码器和解码器。自所以现在这么做是因为 Netty 没有对 Memcached 内置支持。而 HTTP 以及其他标准的协议，Netty 已经是提供的了。

    编码器和解码器

    记住,编码器处理出站，而解码器处理入站。这基本上意味着编码器将编码数据,写入远端。解码器将从远端读取处理数据。重要的是要记住,出站和入站是两个不同的方向。

    请注意,为了程序简单，我们的编码器和解码器不检查任何值的最大大小。在实际实现中你需要一些验证检查,如果检测到违反协议，则使用 EncoderException 或 DecoderException(或一个子类)。

    实现 Memcached 编码器
    本节我们将简要介绍编码器的实现。正如我们提到的,编码器负责编码消息为字节序列。这些字节可以通过网络发送到远端。为了发送请求，我们首先创建 MemcachedRequest 类,稍后编码器实现会编码为一系列字节。下面的清单显示了我们的 MemcachedRequest 类
 */
public class DecoderDemo {
    public static void main(String[] args) {



    }
}


