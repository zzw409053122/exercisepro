package netty.tcp.prototcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MyMessageEncode extends MessageToByteEncoder<MessageProto> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MessageProto msg, ByteBuf out) throws Exception {
        System.out.println("开始编码");
        out.writeInt(msg.getHeard());
        out.writeInt(msg.getLen());
        out.writeBytes(msg.getContent());
    }
}
