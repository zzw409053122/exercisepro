package netty.nettyhttp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.util.CharsetUtil;


public class HttpOutHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println(msg.getClass());
        HttpResponse response = (DefaultFullHttpResponse) msg;
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello 我是服务器2", CharsetUtil.UTF_16);
        response = ((DefaultFullHttpResponse) response).replace(byteBuf);
    }
}
