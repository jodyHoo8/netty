package com.netty.c12Websocket.websocket;


import com.netty.c11Instance.HttpRequestHandler;
import com.netty.c11Instance.TextWebSocketFrameHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

// init a channel initializer
public class ChatServerInitializer
        extends ChannelInitializer<Channel> {
    private final ChannelGroup group;
    public ChatServerInitializer(ChannelGroup group) {
        this.group = group;
    }
    @Override
    protected void initChannel(Channel ch)
              throws Exception {
        // 通过Netty Channel获取一个Pipeline
        // 这里有一个 channelPipeline
        ChannelPipeline pipeline = ch.pipeline();
        // 添加 HttpServerCodec
        // 添加 HttpObjectAggregator
        // 添加 ChunkedWriteHandler
        // 添加 HttpRequestHandler
        // 添加 WebSocketServerProtocolHandler
        // 添加 TextWebSocketFrameHandler
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(64*1024));
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpRequestHandler("/ws"));
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new TextWebSocketFrameHandler(group));
    }
}

