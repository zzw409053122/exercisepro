import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import sortfunction.*;
import sun.security.util.ArrayUtil;

import javax.xml.transform.Result;
import javax.xml.ws.soap.Addressing;
import java.util.*;
import java.util.concurrent.*;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        HeapSort<Integer> integerSelectSort = new HeapSort<Integer>();
//        Integer[] integers = new Integer[]{10,9,8,7,6,5,4,3,2,1,24,654,4,2,4,6};
//        Integer[] integers = new Integer[]{3,2};
        Integer[] integers = new Integer[1000];
        Random random = new Random();
        for (int i = 0; i<integers.length; i ++){
            integers[i] = random.nextInt(1000);
        }
        long l = System.currentTimeMillis();
        integerSelectSort.sort(integers);
        System.out.println(System.currentTimeMillis() - l);
        for (Integer i : integers){
            System.out.println(i);
        }
//        ShellSort<Integer> integerInsertSort2 = new ShellSort<>();
//
//        for (int i = 0; i<integers.length; i ++){
//            integers[i] = random.nextInt(100000);
//        }
//        l = System.currentTimeMillis();
//        integerInsertSort2.sort(integers);
//        System.out.println(System.currentTimeMillis() - l);

    }


}
