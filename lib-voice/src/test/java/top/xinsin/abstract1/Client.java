package top.xinsin.abstract1;

import org.junit.jupiter.api.Test;

/**
 * @Author xinxin
 * @Date 2022/1/15 11:46
 * @Version 1.0
 */
public class Client {
    @Test
    public void Test(){
        System.out.println("=====================小米系列产品=======================");
        //小米工厂
        XiaoMiFactory xiaoMiFactory = new XiaoMiFactory();

        IphoneProduct iphoneProduct = xiaoMiFactory.iphoneProduct();
        iphoneProduct.callUp();
        iphoneProduct.shutdown();

        IRouterProduct iRouterProduct = xiaoMiFactory.iRouterProduct();
        iRouterProduct.start();
        iRouterProduct.openWifi();
    }
}
