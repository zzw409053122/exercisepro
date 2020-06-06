package nio;

import java.nio.IntBuffer;

public class BufferBase {
    public static void main(String[] args) {

        IntBuffer allocate = IntBuffer.allocate(5);

        for (int i = 0; i < allocate.capacity(); i ++)
        {
            allocate.put(2 * i);
        }

        allocate.flip();

        System.out.println(allocate.get());
        System.out.println(allocate.get());
        System.out.println(allocate.get());

        allocate.flip();

        allocate.put(10);

        allocate.flip();
        while (allocate.hasRemaining()) {
            System.out.println(allocate.get());
        }

    }
}
