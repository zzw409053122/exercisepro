package netty.protobuf.multipleproto;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protobuf.StudentPOJO;

import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;

/**
 *
 */
public class CodecServerHandler extends SimpleChannelInboundHandler<MultipleDataInfo.MyMessage> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MultipleDataInfo.MyMessage msg) throws Exception {
        //根据dataType显示不同信息
        MultipleDataInfo.MyMessage.DataType dataType = msg.getDataType();
        if (dataType == MultipleDataInfo.MyMessage.DataType.StudentType) {
            MultipleDataInfo.Student student = msg.getStudent();
            System.out.println("客户端数据：student[id=" + student.getId() + ";name=" + student.getName());
        }else if (dataType == MultipleDataInfo.MyMessage.DataType.WorkerType){
            MultipleDataInfo.Worker worker = msg.getWorker();
            System.out.println("客户端数据：worker[name=" + worker.getName() + ";age=" + worker.getAge());
        }else {
            System.out.println("不支持的类型");
        }
    }

    //异常处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
