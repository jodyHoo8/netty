package com.netty.c11Instance;


/**
    使用 @Test 注解
    新建 ByteBuf 写入 9 个字节
    新建 EmbeddedChannel 并安装一个 FixedLengthFrameDecoder 用于测试
    写入 2 个字节并预测生产的新帧(消息)
    写一帧大于帧的最大容量 (3) 并检查一个 TooLongFrameException 异常
    如果异常没有被捕获，测试将失败。注意如果类实现 exceptionCaught() 并且处理了异常 exception，那么这里就不会捕捉异常
    写剩余的 2 个字节预测一个帧
    标记 channel 完成
    读到的产生的消息并且验证值。注意 assertEquals(Object,Object)测试使用 equals() 是否相当，不是对象的引用是否相当
    即使我们使用 EmbeddedChannel 和 ByteToMessageDecoder。

    应该指出的是，同样的可以做每个 ChannelHandler 的实现，将抛出一个异常。

    乍一看，这看起来很类似于测试我们写在清单10.2中,但它有一个有趣的转折，即 TooLongFrameException 的处理。这里使用的 try/catch 块是 EmbeddedChannel 的一种特殊的特性。如果其中一个“write*"编写方法产生一个受控异常将被包装在一个 RuntimeException。这使得测试更加容易,如果异常处理的一部分处理。
 */
public class FrameChunkDecoderTest {}

