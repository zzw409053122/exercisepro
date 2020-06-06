package chatproject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

public class ChatClient {

    private static final String IP = "localhost";
    private static final int PORT = 6666;
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    public ChatClient() throws IOException {
        selector = Selector.open();
        socketChannel = SocketChannel.open(new InetSocketAddress(IP, PORT));
        //设置非阻塞
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        //得到username
        username = socketChannel.getLocalAddress().toString();
        System.out.println(username + "is ok");
    }

    //向服务器发送消息
    public void sendInfo(String info){
        try {
            info = username + ":" + info;
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //获取服务器端消息
    public void readInfo(){
        try {
            int readCount = selector.select();
            if (readCount > 0) {//有可用通道
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {
                        //得到相关通道
                        SocketChannel channel = (SocketChannel) key.channel();
                        channel.configureBlocking(false);
                        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                        channel.read(readBuffer);
                        String serverStr = new String(readBuffer.array());
                        System.out.println("服务端消息：" + serverStr);
                        //移除当前key
                        iterator.remove();
                    }
                }
            }else{
//                System.out.println("没有可用通道...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ChatClient chatClient = new ChatClient();
        //启动一个线程读取服务端数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    chatClient.readInfo();
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        //发送数据
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String sendStr = scanner.nextLine();
            chatClient.sendInfo(sendStr);
        }
    }
}
