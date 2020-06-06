package netty.tcp.prototcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class MyMessageDecode extends ReplayingDecoder<Void> {
    //基准长度 协议头+协议长度 即为2个int的长度
    private int datumLength = 4 + 4;
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("开始解码");
        //二进制字节码转换为MessageProto数据包
        //起始读包位置
        int beginIndex = 0;
        //数据包长度
        int length = 0;
        if (in.readableBytes() >= datumLength){

            while (true){
                beginIndex = in.readerIndex();
                //标记开始的index
                in.markReaderIndex();
                if (in.readInt() == ConstantValue.HEAD_DATA){
                    break;
                }
            }
            int len = in.readInt();
            byte[] content = new byte[len];
            in.readBytes(content);
            //封装成MessageProto对象
            MessageProto messageProto = new MessageProto();
            messageProto.setLen(len);
            messageProto.setContent(content);
            out.add(messageProto);
        }
    }
}
