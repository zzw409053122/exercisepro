package netty.codec;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //编码器
        pipeline.addLast(new MyLongToByteEncode());
        //
        pipeline.addLast(new MyByteToLongDecode());
        pipeline.addLast(new MyClientHandle());
    }
}
