package netty.dubbo.proxydemo;


public class Client {
    public static void main(String[] args) {
        ITeacherDao teacherDao = new TeacherDao();
        ProxyFactory proxyFactory = new ProxyFactory(teacherDao);
        ITeacherDao proxyInstance = (ITeacherDao) proxyFactory.getProxyInstance();
        proxyInstance.teach();
        String name = proxyInstance.sayMyName("zzw");
    }
}
