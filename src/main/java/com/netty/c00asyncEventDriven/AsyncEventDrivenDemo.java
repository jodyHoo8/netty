package com.netty.c00asyncEventDriven;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
    Netty是什么
    Netty 是一个利用 Java 的高级网络的能力，
    隐藏了Java背后的复杂性然后提供了一个易于使用的 API 的客户端/服务器框架。
    Netty 的高性能和可扩展性，可以作为你自己的独特的应用，
    让你更用心的花时间在你有兴趣的东西上。

    Netty处理一些高并发的网络问题的时候会体现出怎样的价值呢？想知道的话，
    答案就在本章中。本章除了解释上述问题，
    还将为大家讲解Netty的基本概念以及Netty的工具包的构成成分。

    Netty的历史
    在网络发展初期，需要花很多时间来学习 socket 的复杂，寻址等等，在 C socket 库上进行编码，并需要在不同的操作系统上做不同的处理。
    Java 早期版本(1995-2002)介绍了足够的面向对象的糖衣来隐藏一些复杂性，但实现复杂的客户端-服务器协议仍然需要大量的样板代码（和进行大量的监视才能确保他们是对的）。
    这些早期的 Java API（java.net）只能通过原生的 socket 库来支持所谓的“blocking（阻塞）”的功能。一个简单的例子

    Listing 1.1 Blocking I/O Example


    1.ServerSocket 创建并监听端口的连接请求
    2.accept() 调用阻塞，直到一个连接被建立了。返回一个新的 Socket 用来处理 客户端和服务端的交互
    3.流被创建用于处理 socket 的输入和输出数据。BufferedReader 读取从字符输入流里面的本文。PrintWriter 打印格式化展示的对象读到本文输出流
    4.处理循环开始 readLine() 阻塞，读取字符串直到最后是换行或者输入终止。
    5.如果客户端发送的是“Done”处理循环退出
    6.执行方法处理请求，返回服务器的响应
    7.响应发回客户端
    8.处理循环继续
    显然，这段代码限制每次只能处理一个连接。
    为了实现多个并行的客户端我们需要分配一个新的 Thread 给每个新的客户端 Socket(当然需要更多的代码)。
    但考虑使用这种方法来支持大量的同步，长连接。在任何时间点多线程可能处于休眠状态，等待输入或输出数据。
    这很容易使得资源的大量浪费，对性能产生负面影响。当然，有一种替代方案。
    除了示例中所示阻塞调用，原生 socket 库同时也包含了非阻塞 I/O 的功能。
    这使我们能够确定任何一个 socket 中是否有数据准备读或写。我们还可以设置标志，
    因为读/写调用如果没有数据立即返回；
    就是说，如果一个阻塞被调用后就会一直阻塞，直到处理完成。通过这种方法，会带来更大的代码的复杂性成本，
    其实我们可以获得更多的控制权来如何利用网络资源。
 */
public class AsyncEventDrivenDemo {
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(8080);//1
        Socket clientSocket = serverSocket.accept();             //2
        BufferedReader in = new BufferedReader(                     //3
                new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);
        String request, response;
        while ((request = in.readLine()) != null) {
            //4
            if ("Done".equals(request)) {                         //5
                break;
            }
        }
//        response = processRequest(request);                        //6
//        out.println(response);                                    //7
    // }
    }

}

