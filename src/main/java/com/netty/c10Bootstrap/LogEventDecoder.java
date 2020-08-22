package com.netty.c10Bootstrap;


import com.netty.c14UDP.nettyUDP.LogEvent;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
    获取 DatagramPacket 中数据的引用
    获取 SEPARATOR 的索引
    从数据中读取文件名
    读取数据中的日志消息
    构造新的 LogEvent 对象并将其添加到列表中

    第二个 ChannelHandler 将执行一些首先创建的 LogEvent 消息。
    在这种情况下,我们只会写入 system.out。在真实的应用程序可能用到一个单独的日志文件或放到数据库。
 */
public class LogEventDecoder
        extends MessageToMessageDecoder<DatagramPacket> {
    @Override
    protected void decode(ChannelHandlerContext ctx,
                          DatagramPacket datagramPacket,
                          List<Object> out) throws Exception {
        ByteBuf data = datagramPacket.content(); //1
        int i = data.indexOf(0, data.readableBytes(), LogEvent.SEPARATOR);  //2
        String filename = data.slice(0, i).toString(CharsetUtil.UTF_8);  //3
        String logMsg =  data.slice(i + 1, data.readableBytes()).toString(CharsetUtil.UTF_8);  //4
        LogEvent event = new LogEvent(datagramPacket.recipient(), System.currentTimeMillis(),
                filename,logMsg); //5
        out.add(event);
    }
}

