package netty.dubbo.proxydemo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory {
    private Object target;

    //对目标代理对象初始化
    public ProxyFactory(Object target) {
        this.target = target;
    }

    //构建代理目标对象实例
    public Object getProxyInstance(){
        /**
         *
         */
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("代理开始");
                Object returnVal = method.invoke(target, args);
                System.out.println("代理结束");
                return returnVal;
            }
        });
    }
}
