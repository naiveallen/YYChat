package com.yy.yychat.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 *  处理消息的handler
 *  TextWebSocketFrame ： 在netty中，是专门为webSocket处理文本的对象  frame是消息的载体
 */

public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    // 用于记录和管理所有客户端的channel
    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        // 获取客户端传输过来的消息
        String content = msg.text();

        System.out.println("接收到的数据：" + content);

        for (Channel channel : clients) {
            channel.writeAndFlush(
                    new TextWebSocketFrame("[群发]服务器接收到消息：" + content));
        }

        // 与上面的方法一样
        // clients.writeAndFlush(new TextWebSocketFrame("[群发]服务器接收到消息：" + content));

    }


    // 当客户端连接服务器后，即打开了连接
    // 获取客户端的channel 放到clients中进行管理
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        clients.add(channel);
        System.out.println(channel.id().asShortText() + "已加入。");
    }



    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 当客户端关闭连接，clients中会自动remove这个channel
//        clients.remove(channel);
        System.out.println(channel.id().asShortText() + "已离开。");
    }
}
