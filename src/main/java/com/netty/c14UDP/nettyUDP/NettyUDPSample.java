package com.netty.c14UDP.nettyUDP;


/**
    Netty UDP示例
    接下来，在我们的示例应用程序中将会打开一个文件，将每一行作为消息通过 UDP 发到指定的端口。对于熟悉类Unix操作系统的朋友，则可以将其看成一个非常标准的简化版本“syslog（系统日志）”。UDP是一个完美的适合这样的应用程序，因为文件本身就是存储在文件系统中的，所以UDP可以容忍偶尔丢失一行日志文件的事情。此外，应用程序提供了非常有价值的能力有效地处理大量的数据。

    UDP 广播使添加新事件“监视器”接收日志消息一样简单开始一个指定的端口上侦听器程序。然而,这种轻松的访问也提出了一个潜在的安全问题，指出了为什么 UDP 广播往往是在安全的环境中使用。还要注意广播消息可能只能在本地网络,因为路由器经常阻止他们。

    Publish/Subscribe（发布/订阅）

    应用程序，如 syslog 通常归类为“发布/订阅”;生产者或服务发布事件和多个订阅者可以收到它们。


    Netty LogEvent的POJO
    我们知道在消息应用中，数据一般是以 POJO 的形式出现的，除了实际的消息数据，可以保存配置或处理消息。在这个应用程序里，消息的单元是一个“事件”。因为数据来自于一个日志文件，我们就将其称之为LogEvent。
 */
public class NettyUDPSample {
    public static void main(String[] args) {



    }
}

