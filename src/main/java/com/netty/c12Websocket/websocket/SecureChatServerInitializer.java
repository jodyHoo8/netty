package com.netty.c12Websocket.websocket;


import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
    1.扩展 ChatServerInitializer 来实现加密
    2.向 ChannelPipeline 中添加SslHandler
    最后修改 ChatServer，使用 SecureChatServerInitializer 并传入 SSLContext
    Listing 11.7 Add encryption to the ChatServer
 */
public class SecureChatServerInitializer
       extends ChatServerInitializer {
    private final SslContext context;
    public SecureChatServerInitializer(ChannelGroup group,
                                       SslContext context) {
        super(group);
        this.context = context;
    }
    @Override
    protected void initChannel(Channel ch)
              throws Exception {
        super.initChannel(ch);
        SSLEngine engine = context.newEngine(ch.alloc());
        engine.setUseClientMode(false);
        ch.pipeline().addFirst(new SslHandler(engine));
    }
}

