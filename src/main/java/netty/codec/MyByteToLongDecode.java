package netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MyByteToLongDecode extends ByteToMessageDecoder {
    /**
     *
     * @param ctx 上下文对象
     * @param in 入站bytebuf
     * @param out 将数据解码后传给下一个inboundhandler
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("解码器执行");
        //long长度为8个字节
        if (in.readableBytes() >= 8){
            out.add(in.readLong());
        }
    }
}
