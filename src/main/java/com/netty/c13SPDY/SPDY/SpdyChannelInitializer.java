package com.netty.c13SPDY.SPDY;


import com.netty.c12Websocket.websocket.DefaultSpdyOrHttpChooser;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
    继承 ChannelInitializer 是一个简单的开始
    传递 SSLContext 用于创建 SSLEngine
    新建 SSLEngine,用于新的管道和连接
    配置 SSLEngine 用于非客户端使用
    通过 NextProtoNego helper 类绑定 DefaultServerProvider 到 SSLEngine
    添加 SslHandler 到 ChannelPipeline 这将会在协议检测到时保存在 ChannelPipeline
    添加 DefaultSpyOrHttpChooser 到 ChannelPipeline 。这个实现将会监测协议。添加正确的 ChannelHandler 到 ChannelPipeline,并且移除自身

    实际的 ChannelPipeline 设置将会在 DefaultSpdyOrHttpChooser 实现之后完成,因为在这一点上它可能只需要知道客户端是否支持 SPDY
    为了说明这一点,让我们总结一下,看看不同 ChannelPipeline 状态期间与客户连接的生命周期。图12.2显示了在 Channel 初始化后的 ChannelPipeline。

    现在,这取决于客户端是否支持 SPDY,管道将修改DefaultSpdyOrHttpChooser 来处理协议。之后并不需要添加所需的 ChannelHandler 到 ChannelPipeline,所以删除本身。这个逻辑是由抽象 SpdyOrHttpChooser 类封装,DefaultSpdyOrHttpChooser 父类。
    图12.3显示了支持 SPDY 的 ChannelPipeline 用于连接客户端的配置。

    Figure 12.3 ChannelPipeline if SPDY is supported
    每个 ChannelHandler 负责的一小部分工作,这个就是对基于 Netty 构造的应用程序最完美的诠释。每个 ChannelHandler 的职责如表12.3所示。
    Table 12.3 Responsibilities of the ChannelHandlers when SPDY is used


    SslHandler	加解密两端交换的数据
    SpdyFrameDecoder	从接收到的 SPDY 帧中解码字节
    SpdyFrameEncoder	编码 SPDY 帧到字节
    SpdySessionHandler	处理 SPDY session
    SpdyHttpEncoder	编码 HTTP 消息到 SPDY 帧
    SpdyHttpDecoder	解码 SDPY 帧到 HTTP 消息
    SpdyHttpResponseStreamIdHandler	处理基于 SPDY ID 请求和响应之间的映射关系
    SpdyRequestHandler	处理 FullHttpRequest, 用于从 SPDY 帧中解码，因此允许 SPDY 透明传输使用

    Figure 12.3 ChannelPipeline if SPDY is not supported
    和之前一样,每个 ChannelHandler 都有职责,定义在表12.4

    Table 12.4 Responsibilities of the ChannelHandlers when HTTP is used
    名称	职责
    SslHandler	加解密两端交换的数据
    HttpRequestDecoder	从接收到的 HTTP 请求中解码字节
    HttpResponseEncoder	编码 HTTP 响应到字节
    HttpObjectAggregator 处理 SPDY session HttpRequestHandler | 解码时处理 FullHttpRequest

 */
public class SpdyChannelInitializer
        extends ChannelInitializer<SocketChannel> {  //1
//    private final SslContext context;

    public SpdyChannelInitializer(SslContext context) //2
    {
//        this.context = context;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
//        SSLEngine engine = context.newEngine(ch.alloc());  //3
//        engine.setUseClientMode(false);  //4

//        NextProtoNego.put(engine, new DefaultServerProvider());  //5
//        NextProtoNego.debug = true;

//        pipeline.addLast("sslHandler", new SslHandler(engine));  //6
//        pipeline.addLast("chooser", new DefaultSpdyOrHttpChooser(1024 * 1024, 1024 * 1024));
    }
}


