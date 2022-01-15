package top.xinsin.abstract1;

/**
 * @Author xinxin
 * @Date 2022/1/15 11:38
 * @Version 1.0
 */
public class HuaWeiRouter implements IRouterProduct{
    @Override
    public void start() {
        System.out.println("开启华为路由器");
    }

    @Override
    public void shutdown() {
        System.out.println("关闭华为路由器");
    }

    @Override
    public void openWifi() {
        System.out.println("打开华为路由器WiFi");
    }

    @Override
    public void setting() {
        System.out.println("打开华为路由器设置");
    }
}
