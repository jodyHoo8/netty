package com.netty.c13SPDY.SPDY;


/**
    Netty SPDY示例程序
    编写一个简单的服务器应用程序，看看如何将 SPDY 集成到你的下一个应用程序中。客户机会接收到它提供的一些静态内容，而这些内容将由你所使用的协议是 HTTPS 还是 SPDY 决定。如果服务器 SPDY 能够被客户端的浏览器所支持的话，就会自动切换到 SPDY。图12.1显示了应用程序的流程

    Webbrowswer
    Server - Client not support SPDY - HTTPS
           - Client support SPDY - SPDY

    Netty中的SPDY实现
    SPDY 使用 TLS 的扩展称为 Next Protocol Negotiation (NPN)。在Java 中,我们有两种不同的方式选择的基于 NPN 的协议:

    使用 ssl_npn,NPN 的开源 SSL 提供者。
    使用通过 Jetty 的 NPN 扩展库。
 */
public class SPDYSample {
    public static void main(String[] args) {



    }
}


