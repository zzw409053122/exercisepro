package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class NIOServer {

    public static void main(String[] args) throws IOException {

        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);

        //创建ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //创建Selector对象
        Selector selector = Selector.open();
        //绑定服务端口，进行服务端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        //serverSocketChannel注册到selector，关心事件为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //循环等待客户端连接
        while (true) {
            if (selector.select(1000) == 0)
            {//没有事件发生
                continue;
            }
            //有关心事件发生，获取相关的seletionkey集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //通过selectionkeys反向获取channel
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext())
            {
                //获取selectionkey
                SelectionKey key = iterator.next();
                //根据key 对相应事件作相应处理
                if (key.isAcceptable())
                {//有客户端连接
                    //生成socketChannel，这里已经确认有客户端连接了，accept不会阻塞
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //设置为非阻塞！！
                    socketChannel.configureBlocking(false);
                    System.out.println("客户端连接成功~~~socketChannel" + socketChannel.hashCode());
                    //将socketchannel注册到selector上,关注事件为OP_READ,同时给该通道关联一个Buffer
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }
                else if (key.isReadable())
                {
                    readBuffer.clear();
                    //通过key反向获取对应通道
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    socketChannel.read(readBuffer);
                    System.out.println("客户端数据：" + new String(readBuffer.array()));
                    socketChannel.register(selector, SelectionKey.OP_WRITE);
                }
                else if (key.isWritable())
                {
                    writeBuffer.clear();
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    System.out.println("发送数据给客户端>>>>>>>");
//                    Scanner reader = new Scanner(System.in);
//                    String line = reader.nextLine();
                    String line = "ok";
                    writeBuffer.put(line.getBytes());
                    writeBuffer.flip();
                    socketChannel.write(writeBuffer);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }
                //手动从集合中移除当前的selectionkey,防止重复操作
                iterator.remove();
            }
        }
    }
}
