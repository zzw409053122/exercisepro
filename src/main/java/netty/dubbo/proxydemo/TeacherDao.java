package netty.dubbo.proxydemo;

public class TeacherDao implements ITeacherDao{
    @Override
    public void teach() {
        System.out.println("授课中......");
    }

    @Override
    public String sayMyName(String name) {
        System.out.println("输入name:" + name);
        return name;
    }
}
