package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class NIOClient {
    public static void main(String[] args) throws IOException {

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        //设置非阻塞模式
        socketChannel.configureBlocking(false);
        //提供服务器端ip,port
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
        //连接服务器
        if (!socketChannel.connect(inetSocketAddress))
        {//非阻塞循环
            while (!socketChannel.finishConnect()) {
                System.out.println("因为连接需要时间，客户端不会阻塞，可以做其他事");
            }
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("发送数据给服务端>>>>>>>");
            String sendStr = scanner.nextLine();
            buffer.put(sendStr.getBytes());
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
            int readLength = socketChannel.read(buffer);
            if (readLength == -1)
            {
                break;
            }
            buffer.flip();
            byte[] datas = new byte[buffer.remaining()];
            buffer.get(datas);
            System.out.println("服务端消息：" + new String(datas, "UTF-8"));
            buffer.clear();
        }
    }
}
