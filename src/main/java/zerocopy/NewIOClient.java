package zerocopy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NewIOClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 6666));
        String fileName = "Snipaste_2020-04-30_17-30-57.png";
        FileChannel fileChannel = new FileInputStream(fileName).getChannel();

        long nowTime = System.currentTimeMillis();

        //在linux下一个transferTo方法就可以完成传输
        //在windows下一次transferTo只能发送8m大小，需要分段传输文件
        //transferTo底层零拷贝
        long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("发送文件总字节数：" + transferCount + "耗时：" + (System.currentTimeMillis() - nowTime));
        fileChannel.close();
        socketChannel.close();
    }
}
