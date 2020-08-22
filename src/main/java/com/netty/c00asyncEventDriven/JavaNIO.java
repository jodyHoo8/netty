package com.netty.c00asyncEventDriven;


/**
    JAVA NIO
    在 2002 年，Java 1.4 引入了非阻塞 API 在 java.nio 包（NIO）。

    "New"还是"Nonblocking"?
    NIO 最初是为 New Input/Output 的缩写。然而，Java 的 API 已经存在足够长的时间，它不再是新的。现在普遍使用的缩写来表示Nonblocking I/O (非阻塞 I/O)。另一方面，一般（包括作者）指阻塞 I/O 为 OIO 或 Old Input/Output。你也可能会遇到普通 I/O。
    我们已经展示了在 Java 的 I/O 阻塞一例例子。图 1.1 展示了方法 必须扩大到处理多个连接：给每个连接创建一个线程，有些连接是空闲的！显然，这种方法的可扩展性将是受限于可以在 JVM 中创建的线程数。

    当你的应用中连接数比较少，这个方案还是可以接受。
    当并发连接超过10000 时，context-switching（上下文切换）开销将是明显的。
    此外，每个线程都有一个默认的堆栈内存分配了 128K 和 1M 之间的空间。
    考虑到整体的内存和操作系统需要处理 100000 个或更多的并发连接资源，
    这似乎是一个不理想的解决方案。
 */
public class JavaNIO {
    public static void main(String[] args) {

        // 当并发连接超过10000 时，context-switching（上下文切换）开销将是明显的
        // 每个线程都有一个默认的堆栈内存分配了 128K 和 1M 之间的空间
        // 考虑到整体的内存和操作系统需要处理 100000 个或更多的并发连接资源，这似乎是一个不理想的解决方案
        /**
            SELECTOR
            相比之下，图1.2 显示了使用非阻塞I/O，主要是消除了这些方法 约束。在这里，我们介绍了“Selector”，这是 Java 的无阻塞 I/O 实现的关键。
            Figure 1.2 Nonblocking I/O
         */

        // Socket

        //

        //

        //

        /**
            Selector 最终决定哪一组注册的 socket 准备执行 I/O。
            正如我们之前所解释的那样，这 I/O 操作设置为非阻塞模式。通过通知，一个线程可以同时处理多个并发连接。
            （一个 Selector 由一个线程通常处理，但具体实施可以使用多个线程。）
            因此，每次读或写操作执行能立即检查完成。
            总体而言，该模型提供了比 阻塞 I/O 模型 更好的资源使用，因为

            可以用较少的线程处理更多连接，这意味着更少的开销在内存和上下文切换上
            当没有 I/O 处理时，线程可以被重定向到其他任务上。
            如果你要直接使用这些 Java API 构建的 NIO 建立你的应用程序，只是这样做正确和安全是无法保证的。
            实现可靠和可扩展的 event-processing（事件处理器）来处理和调度数据并保证尽可能有效地，
            这是一个繁琐和容易出错的任务，最好留给专家 - Netty。

            面向对象的这个原则的效果显著，
            框架的形式封装解决方案成为了常见的编程任务，
            他们中有许多典型的分布式系统。如今大部分专业的 Java 开发人员都掌握了一个或多个这些框架（比如 Spring），并且许多已成为不可或缺的，使他们能够满足他们的技术要求以及他们的计划。


            Netty 在 2011 年获得了Duke's Choice Award，见https://www.java.net/dukeschoice/2011



            Bootstrap和ServerBootstrap：Netty应用程序通过设置bootstrap引导类来完成，该类提供了一个用于应用程序网络层配置的容器。Bootstrap服务端的是ServerBootstrap，客户端的是Bootstrap。
            Channel：Netty 中的接口 Channel 定义了与 socket 丰富交互的操作集：bind, close, config, connect, isActive, isOpen, isWritable, read, write 等等。
            ChannelHandler：ChannelHandler 支持很多协议，并且提供用于数据处理的容器，ChannelHandler由特定事件触发， 常用的一个接口是ChannelInboundHandler，该类型处理入站读数据（socket读事件）。
            ChannelPipeline：ChannelPipeline 提供了一个容器给 ChannelHandler 链并提供了一个API 用于管理沿着链入站和出站事件的流动。每个 Channel 都有自己的ChannelPipeline，当 Channel 创建时自动创建的。 下图说明了ChannelHandler和ChannelPipeline二者的关系：


            EventLoop：EventLoop 用于处理 Channel 的 I/O 操作。一个单一的 EventLoop通常会处理多个 Channel 事件。一个 EventLoopGroup 可以含有多于一个的 EventLoop 和 提供了一种迭代用于检索清单中的下一个。
            ChannelFuture：Netty 所有的 I/O 操作都是异步。因为一个操作可能无法立即返回，我们需要有一种方法在以后获取它的结果。出于这个目的，Netty 提供了接口 ChannelFuture,它的 addListener 方法
            Netty 是一个非阻塞、事件驱动的网络框架。Netty 实际上是使用 Threads（ 多线程） 处理 I/O事件的，对于熟悉多线程编程的读者可能会需要关注同步代码。这样的方式不好，因为同步会影响程序的性能，Netty 的设计保证程序处理事件不会有同步。因为某个Channel事件是被添加到一个EventLoop中的，以后该Channel事件都是由该EventLoop来处理的，而EventLoop是一个线程来处理的，也就是说Netty不需要同步IO操作，EventLoop与EventLoopGroup的关系可以理解为线程与线程池的关系一样。


            下面展示了 Netty 技术和方法的特点

            设计
            针对多种传输类型的统一接口 - 阻塞和非阻塞
            简单但更强大的线程模型
            真正的无连接的数据报套接字支持
            链接逻辑支持复用

            易用性
            大量的 Javadoc 和 代码实例
            除了在 JDK 1.6 + 额外的限制。（一些特征是只支持在Java 1.7 +。可选的功能可能有额外的限制。）

            性能
            比核心 Java API 更好的吞吐量，较低的延时
            资源消耗更少，这个得益于共享池和重用
            减少内存拷贝

            健壮性
            消除由于慢，快，或重载连接产生的 OutOfMemoryError
            消除经常发现在 NIO 在高速网络中的应用中的不公平的读/写比

            安全
            完整的 SSL / TLS 和 StartTLS 的支持
            运行在受限的环境例如 Applet 或 OSGI

            社区
            发布的更早和更频繁
            社区驱动


            异步和事件驱动
            所有的网络应用程序需要被设计为可扩展性，可以被界定为“一个系统，网络能力，或过程中能够处理越来越多的工作方式或可扩大到容纳增长的能力”（见 Bondi, André B. (2000). "Characteristics of scalability and their impact on performance"）。我们已经说过，Netty 帮助您利用非阻塞 I/O 完成这一目标，通常称为“异步 I/O”

            我们将使用“异步”和其同源词在这本书中大量的使用，所以这是介绍他们的一个很好的时候。异步，即非同步事件，当然是跟你日常生活的类似。例如，您可以发送电子邮件；可能得到或者得不到任何回应，或者当你发送一个您可能会收到一个消息。异步事件也可以有一个有序的关系。例如，你通常不会收到一个问题的答案直到提出一个问题，但是你并没有阻止同时一些其他的东西。

            在日常生活中异步就这样发生了，所以我们不会经常想到。但让计算机程序的工作方式，来实现我们提出了的特殊的问题，会有一点复杂。在本质上，一个系统是异步和“事件驱动”将会表现出一个特定的，对我们来说，有价值的 行为：它可以响应在任何时间以任何顺序发生的事件。

            这是我们要建立一种制度，正如我们将会看到，这是典范的 Netty 自底向上的支持。
         */

    }
}

