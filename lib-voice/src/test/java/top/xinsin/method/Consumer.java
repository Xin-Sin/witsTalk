package top.xinsin.method;

import org.junit.jupiter.api.Test;

/**
 * @Author xinxin
 * @Date 2022/1/15 10:33
 * @Version 1.0
 */
public class Consumer {
    @Test
    public void main(){
        Car car = new WuLingFactory().getCar();
        car.name();
    }
}
