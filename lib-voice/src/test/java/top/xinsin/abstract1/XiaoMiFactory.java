package top.xinsin.abstract1;

/**
 * @Author xinxin
 * @Date 2022/1/15 11:42
 * @Version 1.0
 */
public class XiaoMiFactory implements IProductFactory{
    @Override
    public IphoneProduct iphoneProduct() {
        return new XiaoMiPhone();
    }

    @Override
    public IRouterProduct iRouterProduct() {
        return new XiaoMiRouter();
    }
}
