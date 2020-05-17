package com.yy.yychat.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class NettyServer {

    private static NettyServer Instance;

    public static NettyServer getInstance() {
        if (Instance == null) {
            synchronized(NettyServer.class){
                if (Instance == null) {
                    Instance = new NettyServer();
                }
            }
        }
        return Instance;
    }

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ServerBootstrap server;
    private ChannelFuture channelFuture;

    private NettyServer() {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        server = new ServerBootstrap();
        server.group(bossGroup, workerGroup);
        server.channel(NioServerSocketChannel.class);
        server.childHandler(new ServerInitializer());

    }

    @PostConstruct
    public void start() {
        try {
            channelFuture = server.bind(9999).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (channelFuture.isSuccess()) {
            System.out.println("Netty Server starts...");
        }
    }

    @PreDestroy
    public void close() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        System.out.println("Netty Server has been closed...");
    }

}
