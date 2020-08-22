package com.netty.c13SPDY.SPDY;


public class SPDYDemo {
    public static void main(String[] args) {

        /**
            Netty SPDY 背景介绍
            SPDY 是谷歌开发来解决扩展性的问题的，
            SPDY的主要任务是让加载内容的速度加快，它的工作任务如下：

            每个头都是压缩的，消息体的压缩是可选的,
            因为它可能对代理服务器有问题

            所有的加密都使用 TLS 每个连接多个转移是可能的
            数据集可以单独设置优先级,使关键内容先被转移


            下表是与 HTTP 的对比

            Table 12.1 Comparison of SPDY and HTTP

            浏览器	        HTTP 1.1	    SPDY
            加密	        Not by default	Yes
            Header 压缩	    No	            Yes
            全双工	        No	            Yes
            Server  push    No	            Yes
            优先级	        No	            Yes
         */

    }
}

