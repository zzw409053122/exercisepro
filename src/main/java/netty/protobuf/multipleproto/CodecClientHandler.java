package netty.protobuf.multipleproto;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.protobuf.StudentPOJO;

import java.util.Random;

public class CodecClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * 当通道就绪时就会触发该方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //随机发送Student或者Worker对象
        int random = new Random().nextInt(3);
        MultipleDataInfo.MyMessage myMessage = null;
        if (0 == random) {
            myMessage = MultipleDataInfo.MyMessage.newBuilder().setDataType(
                    MultipleDataInfo.MyMessage.DataType.StudentType).setStudent(
                            MultipleDataInfo.Student.newBuilder().setId(5).setName("sxy").build()).build();
        } else {
            myMessage = MultipleDataInfo.MyMessage.newBuilder().setDataType(
                    MultipleDataInfo.MyMessage.DataType.WorkerType).setWorker(
                    MultipleDataInfo.Worker.newBuilder().setAge(18).setName("sxy").build()).build();
        }
        ctx.writeAndFlush(myMessage);
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
