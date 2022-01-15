package top.xinsin.abstract1;

/**
 * @Author xinxin
 * @Date 2022/1/15 11:42
 * @Version 1.0
 */
public class HuaWeiFactory implements IProductFactory{
    @Override
    public IphoneProduct iphoneProduct() {
        return new HuaWeiPhone();
    }

    @Override
    public IRouterProduct iRouterProduct() {
        return new HuaWeiRouter();
    }
}
