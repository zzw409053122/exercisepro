package netty.dubbo.consumer;

import netty.dubbo.netty.DubboClient;
import netty.dubbo.service.HelloService;

public class Consumer {
    public static void main(String[] args) {
        DubboClient consumer = new DubboClient();
        HelloService bean = (HelloService) consumer.getBean(HelloService.class, "#");
        String res = bean.hello("我是客户端");
        System.out.println(res);
    }
}
