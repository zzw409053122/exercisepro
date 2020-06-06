package nio;

import sun.nio.ch.FileChannelImpl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
/**
 *  MappedByteBuffer 让文件直接在内存（堆外内存）中修改，操作系统不需要拷贝一次
 */
public class MappedBuffer {
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("e:\\file01.txt","rw");
        FileChannel channel = randomAccessFile.getChannel();
        /**
         * 参数1：使用的读写模式
         * 参数2：可以直接修改的起始位置
         * 参数3：映射到内存的大小，不是可修改的索引末尾
         */
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        map.put(0, (byte) 'G');
        map.put(3, (byte) 'H');
        channel.close();
    }
}
