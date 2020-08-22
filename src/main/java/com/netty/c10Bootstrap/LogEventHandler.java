package com.netty.c10Bootstrap;


import com.netty.c14UDP.nettyUDP.LogEvent;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
    继承 SimpleChannelInboundHandler 用于处理 LogEvent 消息
    在异常时，输出消息并关闭 channel
    建立一个 StringBuilder 并构建输出
    打印出 LogEvent 的数据
    LogEventHandler 打印出 LogEvent 的一个易读的格式,包括以下:

    收到时间戳以毫秒为单位
    发送方的 InetSocketAddress,包括IP地址和端口
    LogEvent 生成绝对文件名
    实际的日志消息,代表在日志文件中一行
    现在我们需要安装处理程序到 ChannelPipeline ，如图13.4所示。
    下一个清单显示了这是如何实现 LogEventMonitor 类的一部分。
 */
public class LogEventHandler
        extends SimpleChannelInboundHandler<LogEvent> { //1
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace(); //2
        ctx.close();
    }
    @Override
    public void channelRead0(ChannelHandlerContext channelHandlerContext, LogEvent event) throws Exception {
        StringBuilder builder = new StringBuilder(); //3
        builder.append(event.getReceivedTimestamp());
        builder.append(" [");
        builder.append(event.getSource().toString());
        builder.append("] [");
        builder.append(event.getLogfile());
        builder.append("] : ");
        builder.append(event.getMsg());

        System.out.println(builder.toString()); //4
    }
}

