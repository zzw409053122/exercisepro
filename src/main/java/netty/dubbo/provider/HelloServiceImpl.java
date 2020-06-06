package netty.dubbo.provider;

import netty.dubbo.service.HelloService;

public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String msg) {
        if (msg != null && !msg.equals("")) {
            System.out.println("服务收到客户端消息:" + msg);
            return "server response";
        }else {
            return "null";
        }
    }
}
