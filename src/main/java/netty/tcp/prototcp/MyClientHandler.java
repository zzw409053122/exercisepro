package netty.tcp.prototcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class MyClientHandler extends SimpleChannelInboundHandler<MessageProto> {
    private int count;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //使用客户端发送10条数据
        for (int i = 0; i < 10; i++){
            String msg = "Hello" + i;
            byte[] content = msg.getBytes(CharsetUtil.UTF_8);
            int len = msg.getBytes(CharsetUtil.UTF_8).length;
            MessageProto messageProto = new MessageProto();
            messageProto.setLen(len);
            messageProto.setContent(content);
            ctx.writeAndFlush(messageProto);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProto msg) throws Exception {
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.getCause());
        ctx.close();
    }
}
