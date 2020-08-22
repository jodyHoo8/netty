package com.netty.c13SPDY.SPDY;


import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
    定义所有的 ServerProvider 实现的协议
    设置如果 SPDY 协议失败了就转到 http/1.1
    返回支持的协议的列表
    设置选择的协议
    返回选择的协议
    在 ServerProvider 的实现，我们支持下面的3种协议:

    SPDY 2
    SPDY 3
    HTTP 1.1
    如果客户端不支持 SPDY ，则默认使用 HTTP 1.1

    实现各种 ChannelHandler
    第一个 ChannelInboundHandler 是用于不支持 SPDY 的情况下处理客户端 HTTP 请求，如果不支持 SPDY 就回滚使用默认的 HTTP 协议。

    清单12.2显示了HTTP流量的处理程序。


    重写 channelRead0() ,可以被所有的接收到的 FullHttpRequest 调用
    检查如果接下来的响应是预期的，就写入
    新建 FullHttpResponse,用于对请求的响应
    生成响应的内容，将它写入 payload
    设置头文件，这样客户端就能知道如何与 响应的 payload 交互
    检查请求设置是否启用了 keepalive;如果是这样,将标题设置为符合HTTP RFC
    写响应给客户端，并获取到 Future 的引用，用于写完成时，获取到通知
    如果响应不是 keepalive，在写完成时关闭连接
    返回内容作为响应的 payload
    Helper 方法生成了100 持续的响应，并写回给客户端
    若执行阶段抛出异常，则关闭管道
    这就是 Netty 处理标准的 HTTP 。你可能需要分别处理特定　URI ,应对不同的状态代码,这取决于资源存在与否,但基本的概念将是相同的。

    我们的下一个任务将会提供一个组件来支持 SPDY 作为首选协议。 Netty 提供了简单的处理 SPDY 方法。这些将使您能够重用FullHttpRequest 和 FullHttpResponse 消息，通过 SPDY 透明地接收和发送他们。

    HttpRequestHandler 虽然是我们可以重用代码,我们将改变我们的内容写回客户端只是强调协议变化;通常您会返回相同的内容。下面的清单展示了实现,它扩展了先前的 HttpRequestHandler。


    继承 HttpRequestHandler 这样就能共享相同的逻辑
    生产内容写到 payload。这个重写了 HttpRequestHandler 的 getContent() 的实现
    SpdyRequestHandler 继承自 HttpRequestHandler,但区别是:写入的内容的 payload 状态的响应是在 SPDY 写的。

    我们可以实现两个处理程序逻辑,将选择一个相匹配的协议。然而添加以前写过的处理程序到 ChannelPipeline 是不够的;正确的编解码器还需要补充。它的责任是检测传输字节数,然后使用 FullHttpResponse 和 FullHttpRequest 的抽象进行工作。

    Netty 的附带一个基类,完全能做这个。所有您需要做的是实现逻辑选择协议和选择适当的处理程序。
 */
@ChannelHandler.Sharable
public class HttpRequestHandler
        extends SimpleChannelInboundHandler<FullHttpRequest> {
    @Override
    public void channelRead0(ChannelHandlerContext ctx,
                             FullHttpRequest request) throws Exception { //1
        if (HttpHeaders.is100ContinueExpected(request)) {
            send100Continue(ctx); //2
        }
        FullHttpResponse response = new DefaultFullHttpResponse(request.getProtocolVersion(), HttpResponseStatus.OK); //3
        response.content().writeBytes(getContent().getBytes(CharsetUtil.UTF_8));  //4
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain; charset=UTF-8");  //5
        boolean keepAlive = HttpHeaders.isKeepAlive(request);
        if (keepAlive) {  //6
            response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, response.content().readableBytes());
            response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        }
        ChannelFuture future = ctx.writeAndFlush(response);  //7
        if (!keepAlive) {
            future.addListener (ChannelFutureListener.CLOSE); //8
        }
    }
    protected String getContent() {  //9
        return "This content is transmitted via HTTP\r\n";
    }
    private static void send100Continue(ChannelHandlerContext ctx) {  //10
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
        ctx.writeAndFlush(response);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {  //11
        cause.printStackTrace();
        ctx.close();
    }
}


