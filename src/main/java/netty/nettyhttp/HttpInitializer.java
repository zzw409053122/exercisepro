package netty.nettyhttp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 处理器的初始化
 */
public class HttpInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //对于每个客户端来说,pipeline是相互独立的,因此handle也是独立的
        //http请求用完后就会断，所以每次请求都会生成新的pipeline
        ChannelPipeline pipeline = ch.pipeline();
        //HttpServerCodec是netty提供的处理http的编-解码器
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
        pipeline.addLast("MyHttpInHandle", new HttpInHandler());
        pipeline.addLast("MyHttpOutHandle", new HttpOutHandler());
    }
}
