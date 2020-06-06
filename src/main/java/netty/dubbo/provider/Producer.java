package netty.dubbo.provider;

import netty.dubbo.netty.DubboServer;

public class Producer {
    public static void main(String[] args) {
        DubboServer.start("127.0.0.1", 7000);
    }
}
