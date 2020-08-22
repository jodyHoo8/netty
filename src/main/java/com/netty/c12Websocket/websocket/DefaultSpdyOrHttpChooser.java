package com.netty.c12Websocket.websocket;


import com.netty.c13SPDY.SPDY.DefaultServerProvider;

import javax.net.ssl.SSLEngine;

/**
    使用 NextProtoNego 用于获取 DefaultServerProvider 的引用, 用于 SSLEngine
    协议不能被检测到。一旦字节已经准备好读,检测过程将重新开始。
    SPDY 2 被检测到
    SPDY 3 被检测到
    HTTP 1.1 被检测到
    未知协议被检测到
    将会被调用给 FullHttpRequest 消息添加处理器。该方法只会在不支持 SPDY 时调用，那么将会使用 HTTPS
    将会被调用给 FullHttpRequest 消息添加处理器。该方法在支持 SPDY 时调用
    该实现要注意检测正确的协议并设置 ChannelPipeline 。它可以处理SPDY 版本 2、3 和 HTTP 1.1,但可以很容易地修改 SPDY 支持额外的版本。
 */
public class DefaultSpdyOrHttpChooser
        /*extends SpdyOrHttpChooser*/ {

//    public DefaultSpdyOrHttpChooser(int maxSpdyContentLength, int maxHttpContentLength) {
//        super(maxSpdyContentLength, maxHttpContentLength);
//    }

//    @Override
//    protected SelectedProtocol getProtocol(SSLEngine engine) {
//        DefaultServerProvider provider = (DefaultServerProvider) NextProtoNego.get(engine);  //1
//        String protocol = provider.getSelectedProtocol();
//        if (protocol == null) {
//            return SelectedProtocol.UNKNOWN; //2
//        }
//        switch (protocol) {
//            case "spdy/2":
//                return SelectedProtocol.SPDY_2; //3
//            case "spdy/3.1":
//                return SelectedProtocol.SPDY_3_1; //4
//            case "http/1.1":
//                return SelectedProtocol.HTTP_1_1; //5
//            default:
//                return SelectedProtocol.UNKNOWN; //6
//        }
//    }

//    @Override
//    protected ChannelInboundHandler createHttpRequestHandlerForHttp() {
//        return new HttpRequestHandler(); //7
//    }

//    @Override
//    protected ChannelInboundHandler createHttpRequestHandlerForSpdy() {
//        return new SpdyRequestHandler();  //8
//    }
}


