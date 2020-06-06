package chatproject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class ChatServer {
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6666;

    //初始化
    public ChatServer() {
        try {
            //得到选择器
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();
            //绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            //设置非阻塞模式
            listenChannel.configureBlocking(false);
            //listenChannel注册到selector上
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        try {
            while (true) {
                int count = selector.select(1000);
                if (count > 0) {//有事件处理
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        //监听到accept
                        if (key.isAcceptable()) {
                            SocketChannel socketChannel = listenChannel.accept();
                            socketChannel.configureBlocking(false);
                            //将socketchannel注册到selector上
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            //客户端连接上了
                            System.out.println(socketChannel.getRemoteAddress() + "上线了");
                        }
                        else if (key.isReadable()){//发生read事件
                            //处理读取事件
                            handleRead(key);
                        }

                        //移除当前key，防止重复处理
                        iterator.remove();
                    }
                }
                else {
//                    System.out.println("等待中.....");
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {

        }
    }

    private void handleRead(SelectionKey key) {
        SocketChannel channel = null;
        try {
            //通过selectionkey反向获取通道
            channel = (SocketChannel) key.channel();
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            int readCount = channel.read(readBuffer);
            if (readCount > 0) {
                //把缓冲区数据转成字符串
                String clientStr = new String(readBuffer.array());
                System.out.println(channel.getRemoteAddress() + "发出消息:" + clientStr);
                //向其他客户端转发消息
                forwardMessage(clientStr, channel);
            }
        }
        catch (IOException e){
            try {
                System.out.println(channel.getRemoteAddress() + "离线了");
                //取消注册
                key.cancel();
                //关闭通道
                channel.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }

    //转发消息,排除发出消息的客户端
    private void forwardMessage(String clientStr, SocketChannel channel) throws IOException {
        System.out.println("转发消息中......");
        for(SelectionKey key : selector.keys()){
            //通过key取出channel
            Channel keyChannel = key.channel();
            //排除发出消息的客户端
            if (keyChannel instanceof SocketChannel && keyChannel != channel)
            {
                //转型
                SocketChannel targetChannel = (SocketChannel) keyChannel;
                ByteBuffer buffer = ByteBuffer.wrap(clientStr.getBytes());
                //将buffer数据写入目标通道中
                targetChannel.write(buffer);
            }
        }
    }

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        //进行监听
        chatServer.listen();
    }
}
