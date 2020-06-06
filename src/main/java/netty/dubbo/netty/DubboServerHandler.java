package netty.dubbo.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.dubbo.provider.HelloServiceImpl;

public class DubboServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //获取客户端消息，并调用服务
        System.out.println("msg=" + msg);
        //客户端在调用服务器服务时，需要自定义一个协议
        if (msg.toString().startsWith("#")){
            String response = new HelloServiceImpl().hello(msg.toString().substring(msg.toString().indexOf("#") + 1));
            ctx.writeAndFlush(response);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
