import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import sortfunction.SelectSort;
import sun.security.util.ArrayUtil;

import javax.xml.transform.Result;
import javax.xml.ws.soap.Addressing;
import java.util.*;
import java.util.concurrent.*;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        SelectSort<Integer> integerSelectSort = new SelectSort<Integer>();
        Integer[] integers = new Integer[]{3,2,5,651,63,2,3};
        integerSelectSort.sort(integers);
        for (Integer i : integers){
            System.out.println(i);
        }
    }


}
