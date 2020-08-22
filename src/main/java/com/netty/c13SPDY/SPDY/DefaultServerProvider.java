package com.netty.c13SPDY.SPDY;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
 */
public class DefaultServerProvider
        /*implements NextProtoNego.ServerProvider*/ {
    private static final List<String> PROTOCOLS =
            Collections.unmodifiableList(Arrays.asList("spdy/2", "spdy/3", "http/1.1"));  //1

    private String protocol;

//    @Override
    public void unsupported() {
        protocol = "http/1.1";   //2
    }

//    @Override
    public List<String> protocols() {
        return PROTOCOLS;   //3
    }

//    @Override
    public void protocolSelected(String protocol) {
        this.protocol = protocol;  //4
    }

    public String getSelectedProtocol() {
        return protocol;  //5
    }
}


