package top.xinsin.abstract1;

/**
 * @Author xinxin
 * @Date 2022/1/15 11:40
 * @Version 1.0
 */
//抽象产品工厂
public interface IProductFactory {
    //生产手机
    IphoneProduct iphoneProduct();
    //生产路由器
    IRouterProduct iRouterProduct();
}
