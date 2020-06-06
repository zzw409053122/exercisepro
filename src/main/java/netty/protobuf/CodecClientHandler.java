package netty.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class CodecClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * 当通道就绪时就会触发该方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //生成一个Student对象到服务端
        StudentPOJO.Student student = StudentPOJO.Student.newBuilder().setId(4).setName("zzw").build();
        ctx.writeAndFlush(student);
    }

    /**
     * 数据读取事件
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
