package nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelBase {
    public static void main(String[] args) {

        copyByTransfer();
    }

    public static void inStream()
    {
        try {
            File file = new File("e:\\file01.txt");
            FileInputStream fileInputStream = new FileInputStream(file);
            FileChannel channel = fileInputStream.getChannel();

            ByteBuffer allocate = ByteBuffer.allocate((int) file.length());
            channel.read(allocate);

            channel.close();
            fileInputStream.close();

            System.out.println(new String(allocate.array()));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void outStream()
    {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("e:\\file01.txt");
            FileChannel channel = fileOutputStream.getChannel();

            String data = "channel test";

            ByteBuffer allocate = ByteBuffer.allocate(1024);
            allocate.put(data.getBytes());
            allocate.flip();
            channel.write(allocate);

            channel.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copy()
    {
        try {
            FileInputStream fileInputStream = new FileInputStream("e:\\file01.txt");
            FileChannel inChannel = fileInputStream.getChannel();

            FileOutputStream fileOutputStream = new FileOutputStream("e:\\file02.txt");
            FileChannel outChannel = fileOutputStream.getChannel();

            ByteBuffer interBuffer = ByteBuffer.allocate(1024);
            inChannel.read(interBuffer);

            interBuffer.flip();
            outChannel.write(interBuffer);

            inChannel.close();
            outChannel.close();
            fileInputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void copyByTransfer()
    {

        try {
            FileInputStream fileInputStream = new FileInputStream("e:\\1.png");
            FileChannel inChannel = fileInputStream.getChannel();

            FileOutputStream fileOutputStream = new FileOutputStream("e:\\2.png");
            FileChannel outChannel = fileOutputStream.getChannel();

            outChannel.transferFrom(inChannel, 0 ,inChannel.size());

            inChannel.close();
            outChannel.close();
            fileInputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
