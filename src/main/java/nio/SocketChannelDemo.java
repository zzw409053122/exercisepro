package nio;

import java.lang.reflect.Array;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class SocketChannelDemo {

    public static void main(String[] args) throws Exception {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        InetSocketAddress inetSocketAddress = new InetSocketAddress(6666);

        serverSocketChannel.socket().bind(inetSocketAddress);

        SocketChannel socketChannel = serverSocketChannel.accept();

        ByteBuffer[] byteBuffers = new ByteBuffer[2];

        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(5);

        int maxLength = 10;

        while (true) {
            int readLength = 0;

            while (readLength < maxLength) {
                long read = socketChannel.read(byteBuffers);
                readLength += read;
                Arrays.asList(byteBuffers).stream().map(buffer -> "position" + buffer.position() + "limit" + buffer.limit())
                        .forEach(System.out::println);
            }

            Arrays.asList(byteBuffers).forEach(buffer -> {
                buffer.flip();
            });

            socketChannel.write(byteBuffers);
            Arrays.asList(byteBuffers).forEach(buffer -> {
                buffer.clear();
            });

        }
    }
}
