package top.xinsin.method;

/**
 * @Author xinxin
 * @Date 2022/1/15 10:32
 * @Version 1.0
 */
public class WuLingFactory implements CarFactory{
    @Override
    public Car getCar() {
        return new WuLing();
    }
}
