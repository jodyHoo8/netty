package com.netty.c11Instance;


import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
    1.扩展 ChannelInitializer
    2.添加 ChannelHandler　到 ChannelPipeline

    Table 11.2 ChannelHandlers for the WebSockets Chat server

    ChannelHandler　	　职责
    HttpServerCodec	Decode bytes to
    HttpRequest,
    HttpContent,
    LastHttpContent.Encode
    HttpRequest,
    HttpContent,
    LastHttpContent to bytes.
    ChunkedWriteHandler	Write the contents of a file.
    HttpObjectAggregator
    This ChannelHandler aggregates an HttpMessage and its following
    HttpContents into a single FullHttpRequest or FullHttpResponse
    (depending on whether it is being used to handle requests or responses).With
    this installed the next ChannelHandler in the pipeline will receive only full HTTP requests.
    HttpRequestHandler	Handle FullHttpRequests (those not sent to "/ws" URI).
    WebSocketServerProtocolHandler
    As required by the
    WebSockets specification,
    handle the WebSocket
    Upgrade handshake,
    PingWebSocketFrames,
    PongWebSocketFrames and
    CloseWebSocketFrames.
    TextWebSocketFrameHandler
    Handles TextWebSocketFrames and handshake completion events
    该 WebSocketServerProtocolHandler 处理所有规定的 WebSocket 帧类型和升级握手本身。如果握手成功所需的 ChannelHandler 被添加到管道，而那些不再需要的则被去除。管道升级之前的状态如下图。这代表了 ChannelPipeline 刚刚经过 ChatServerInitializer 初始化。

    握手升级成功后 WebSocketServerProtocolHandler 替换HttpRequestDecoder
    为 WebSocketFrameDecoder，HttpResponseEncoder 为WebSocketFrameEncoder。
    为了最大化性能，WebSocket 连接不需要的 ChannelHandler 将会被移除。
    其中就包括了 HttpObjectAggregator 和 HttpRequestHandler
    下图，展示了 ChannelPipeline 经过这个操作完成后的情况。注意 Netty 目前支持四个版本 WebSocket 协议，
    每个通过其自身的方式实现类。选择正确的版本WebSocketFrameDecoder 和 WebSocketFrameEncoder 是自动进行的，
    这取决于在客户端（在这里指浏览器）的支持（在这个例子中，我们假设使用版本是 13 的 WebSocket 协议，
     从而图中显示的是 WebSocketFrameDecoder13 和 WebSocketFrameEncoder13）。
 */
public class ChatServerInitializer extends ChannelInitializer<Channel> {    //1
    private final ChannelGroup group;
    public ChatServerInitializer(ChannelGroup group) {
        this.group = group;
    }
    @Override
    protected void initChannel(Channel ch) throws Exception {            //2
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(64 * 1024));
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpRequestHandler("/ws"));
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new TextWebSocketFrameHandler(group));
    }
}

