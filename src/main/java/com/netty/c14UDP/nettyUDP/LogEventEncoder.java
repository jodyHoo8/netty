package com.netty.c14UDP.nettyUDP;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.List;

/**
    日志文件
    日志文件中的日志实体
    一个 DatagramPacket 保持一个单独的日志实体

    正如我们所看到的,所有的数据传输都封装在 LogEvent 消息里。LogEventBroadcaster 写这些通过在本地端的管道,发送它们通过ChannelPipeline 转换(编码)为一个定制的 ChannelHandler 的DatagramPacket 信息。最后,他们通过 UDP 广播并被远程接收。
    编码器和解码器
    编码器和解码器将消息从一种格式转换为另一种,深度探讨在第7章中进行。我们探索 Netty 提供的基础类来简化和实现自定义 ChannelHandler 如 LogEventEncoder 在这个应用程序中。

    LogEventEncoder 创建了 DatagramPacket 消息类发送到指定的 InetSocketAddress
    写文件名到 ByteBuf
    添加一个 SEPARATOR
    写一个日志消息到 ByteBuf
    添加新的 DatagramPacket 到出站消息
 */
public class LogEventEncoder
        extends MessageToMessageEncoder<LogEvent> {
    private final InetSocketAddress remoteAddress;
    public LogEventEncoder(InetSocketAddress remoteAddress) {  //1
        this.remoteAddress = remoteAddress;
    }
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext,
                          LogEvent logEvent,
                          List<Object> out) throws Exception {
        byte[] file = logEvent.getLogfile().getBytes(CharsetUtil.UTF_8); //2
        byte[] msg = logEvent.getMsg().getBytes(CharsetUtil.UTF_8);
        ByteBuf buf = channelHandlerContext.alloc().buffer(file.length + msg.length + 1);
        buf.writeBytes(file);
        buf.writeByte(LogEvent.SEPARATOR); //3
        buf.writeBytes(msg);  //4
        out.add(new DatagramPacket(buf, remoteAddress));  //5
    }
}

