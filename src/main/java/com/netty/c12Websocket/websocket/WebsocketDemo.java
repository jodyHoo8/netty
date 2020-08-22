package com.netty.c12Websocket.websocket;


/**
    Netty WebSocket 程序示例
    实时功能到底有什么吸引人的特点呢？
    下面，我们就用 WebSocket 协议来实现一个基于浏览器的实时聊天程序来说明它的特点。
    虽然它可能与在 Facebook 中的聊天操作有所类似，
    但是我们的目的是要让使用它的用户能够同时互相交谈，这样才能体现出它的特别之处不是？

    ＃1客户端/用户连接到服务器，并且是聊天的一部分
    ＃2聊天消息通过 WebSocket 进行交换
    ＃3消息双向发送
    ＃4服务器处理所有的客户端/用户

    逻辑很简单：

    1.客户端发送一个消息。
    2.消息被广播到所有其他连接的客户端。
    这正如你所想的聊天室的工作方式：每个人都可以跟其他人聊天。
    此例子将仅提供服务器端，浏览器充当客户端，通过访问网页来聊天。
    正如您接下来要看到的，WebSocket 让这一切变得简单。

    Netty添加 WebSocket 支持
    一种被称作“Upgrade handshake（升级握手）”的机制能够将标准的HTTP或者HTTPS协议转成 WebSocket。所以，应用程序如果使用了 WebSocket ，那么它都是以 HTTP/S 开始，之后再进行升级，升级会发生在什么时候是不确定的，要根据具体的应用来决定：可能是在应用启动的时候，也可能是当一个特定的 URL 被请求的时候。

    在我们的应用中，要想升级协议为 WebSocket，只有当 URL 请求以“/ws”结束时才可以，如果没有达到该要求，服务器仍将使用基本的 HTTP/S，一旦连接升级，之后的数据传输都将使用 WebSocket 。
 */
public class WebsocketDemo {
    public static void main(String[] args) {

        // 一种被称作“Upgrade handshake（升级握手）”的机制能够将标准的HTTP或者HTTPS协议转成 WebSocket

        // 所以，应用程序如果使用了 WebSocket ，那么它都是以 HTTP/S 开始，之后再进行升级，
        // 升级会发生在什么时候是不确定的，
        // 要根据具体的应用来决定：可能是在应用启动的时候，也可能是当一个特定的 URL 被请求的时候。

        // 在我们的应用中，要想升级协议为 WebSocket，只有当 URL 请求以“/ws”结束时才可以，
        // 如果没有达到该要求，服务器仍将使用基本的 HTTP/S，一旦连接升级，之后的数据传输都将使用 WebSocket 。

    }
}

