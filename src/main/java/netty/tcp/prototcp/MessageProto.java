package netty.tcp.prototcp;

/**
 * 协议包
 */
public class MessageProto {
    //规定协议的最大长度，作为协议头
    private int heard = ConstantValue.HEAD_DATA;
    private int len;
    private byte[] content;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public int getHeard() {
        return heard;
    }
}
