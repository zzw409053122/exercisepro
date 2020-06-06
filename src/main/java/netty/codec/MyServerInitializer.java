package netty.codec;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //解码器
        pipeline.addLast(new MyByteToLongDecode());
        //编码器
        pipeline.addLast(new MyLongToByteEncode());
        //业务处理器
        pipeline.addLast(new MyServerHandler());
    }
}
