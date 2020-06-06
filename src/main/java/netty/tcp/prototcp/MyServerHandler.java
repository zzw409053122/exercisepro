package netty.tcp.prototcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<MessageProto> {
    private int count;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProto msg) throws Exception {
        int length = msg.getLen();
        byte[] content = msg.getContent();
        System.out.println("服务端接收数据：长度：" + length + "内容：" + new String(content, CharsetUtil.UTF_8));
        System.out.println("服务器接收消息次数" + (++this.count));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.getCause());
        ctx.close();
    }
}
