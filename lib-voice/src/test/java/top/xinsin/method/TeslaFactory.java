package top.xinsin.method;

/**
 * @Author xinxin
 * @Date 2022/1/15 10:30
 * @Version 1.0
 */
public class TeslaFactory implements CarFactory{
    @Override
    public Car getCar() {
        return new Tesla();
    }
}
