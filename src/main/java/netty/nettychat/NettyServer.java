package netty.nettychat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


public class NettyServer {

    public static void main(String[] args) throws InterruptedException {
        //bossGroup负责处理与客户端的连接
        //workGroup负责处理客户端数据
        //两者都是无限循环
        //含有子线程个数：默认cpu核数*2
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            //创建服务器端的启动对象，配置启动参数
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //使用链式编程设置
            serverBootstrap.group(bossGroup, workGroup)//设置主从线程组
                    .channel(NioServerSocketChannel.class)//设置服务器通道类型
                    .option(ChannelOption.SO_BACKLOG, 128)//设置线程队列得到连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {//创建一个通道初始化对象
                        //给pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //pipiline中加入解码器
                            ch.pipeline().addLast("decode", new StringDecoder());
                            //pipiline中加入解码器
                            ch.pipeline().addLast("encode", new StringEncoder());
                            ch.pipeline().addLast("chatserver", new NettyServerHandler());
                        }
                    });//给workGroup中的EventLoop对应的pipeline设置处理器

            //绑定一个端口并且启动服务器，生成一个future对象
            ChannelFuture channelFuture = serverBootstrap.bind(6666).sync();
            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
