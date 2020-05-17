package com.yy.yychat.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        // webSocket 基于http  所以需要http编解码器
        pipeline.addLast(new HttpServerCodec());

        // 支持写大数据流
        pipeline.addLast(new ChunkedWriteHandler());

        // 对HttpMessage进行聚合，聚合成FullHttpRequest或者FullHttpResponse
        // 几乎所有在netty中的编程，都会使用这个handler
        pipeline.addLast(new HttpObjectAggregator(1024*64));


        // =================以上是用于支持http协议==================


        // webSocket服务器处理的协议 用于指定给客户端连接访问的路由
        pipeline.addLast(new WebSocketServerProtocolHandler("/chat"));

        pipeline.addLast(new ChatHandler());

    }
}
