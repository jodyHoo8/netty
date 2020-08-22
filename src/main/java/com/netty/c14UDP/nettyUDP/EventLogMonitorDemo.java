package com.netty.c14UDP.nettyUDP;


/**
    这一节我们学习监视器的编写：EventLogMonitor ，也就是用来接收事件的程序，
    用来代替 netcat 。EventLogMonitor 做下面事情：

    接收 LogEventBroadcaster 广播的 UDP DatagramPacket
    解码 LogEvent 消息
    输出 LogEvent 消息
    和之前一样,将实现自定义 ChannelHandler 的逻辑。图13.4描述了LogEventMonitor 的 ChannelPipeline
    并表明了 LogEvent 的流经情况。

    图中显示我们的两个自定义 ChannelHandlers,LogEventDecoder 和 LogEventHandler。
    首先是负责将网络上接收到的 DatagramPacket 解码到 LogEvent 消息。清单13.6显示了实现。
 */
public class EventLogMonitorDemo {
    public static void main(String[] args) {



    }
}

