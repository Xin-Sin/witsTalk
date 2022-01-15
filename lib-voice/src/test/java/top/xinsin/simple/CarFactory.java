package top.xinsin.simple;

/**
 * @Author xinxin
 * @Date 2022/1/15 10:17
 * @Version 1.0
 */
public class CarFactory {
    public static Car getCar(String carName){
        if ("五菱".equals(carName)){
            return new WuLing();
        }else if ("特斯拉".equals(carName)){
            return new Tesla();
        }else {
            return null;
        }
    }
}
