package com.example.musciws.Netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Netty服务
 */
public class NettyService {
    public NettyService() {
        System.out.println("------------启动Netty!-----------");
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new MyChannelInitializer());
            Channel channel = serverBootstrap.bind(9099).sync().channel();
            channel.closeFuture().sync();
        }catch (Exception e){
            System.err.println(e);
        }finally {
          bossGroup.shutdownGracefully();
          workGroup.shutdownGracefully();
        }
    }
}
