package netty.dubbo.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

public class DubboClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private static ChannelHandlerContext context;
    private String result;//服务端返回结果
    private String para;//调用服务参数

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务器" + ctx.channel().remoteAddress());
        context = ctx;//在其他方法中会用到
    }

    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg.toString();
        //唤醒等待线程
        notify();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    void setPara(String para){
        this.para = para;
    }

    //被代理对象调用,发送数据给服务器，等待被唤醒channelRead
    @Override
    public synchronized Object call() throws Exception {
        System.out.println("服务器" + context.channel().remoteAddress());
        context.writeAndFlush(para);
        wait();//等待服务器返回数据给channelRead
        return result;
    }
}
