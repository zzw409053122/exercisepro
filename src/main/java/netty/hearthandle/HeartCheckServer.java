package netty.hearthandle;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 *  心跳监测机制
 */
public class HeartCheckServer {
    public static void main(String[] args) throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))//日志处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //IdleStateHandler是netty提供的处理空闲状态的处理器
                            //long readerIdleTime 表示多少时间没读，会发送一个心跳检测包检测是否连接
                            //long writerIdleTime 表示多少时间没写，会发送一个心跳检测包检测是否连接
                            //long allIdleTime 表示多少时间没读/写，会发送一个心跳检测包检测是否连接
                            // * Triggers an {@link IdleStateEvent} when a {@link Channel} has not performed
                            // * read, write, or both operation for a while.
                            //IdleStateEvent触发后，将IdleStateEvent传递给pipeline中下一个handler处理，
                            //调用下一个handler的userEventTiggered
                            pipeline.addLast(new IdleStateHandler(3,5,6, TimeUnit.SECONDS));
                            pipeline.addLast(new HeartHandle());
                        }
                    });

            ChannelFuture channelFuture = serverBootstrap.bind(7777).sync();
            channelFuture.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }


    }
}
