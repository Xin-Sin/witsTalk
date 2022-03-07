package cn.wzpmc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("cn.wzpmc.dao")
@SpringBootApplication
public class ChatStart implements CommandLineRunner {
    @Autowired
    private Netty netty;
    public static void main(String[] args){
        System.out.println("_ooOoo_");
        System.out.println("o8888888o");
        System.out.println("88\" . \"88");
        System.out.println("(| -_- |)");
        System.out.println("O\\ = /O");
        System.out.println("____/`---'\\____");
        System.out.println(".' \\| |// `.");
        System.out.println("/ \\||| : |||// \\");
        System.out.println("/ _||||| -:- |||||- \\");
        System.out.println("| | \\ - /// | |");
        System.out.println("| \\_| ''\\---/'' | |");
        System.out.println("\\ .-\\__ `-` ___/-. /");
        System.out.println("___`. .' /--.--\\ `. . __");
        System.out.println(".\"\" '< `.___\\__/___.' >'\"\".");
        System.out.println("| | : `- \\`.;`\\ _ /`;.`/ - ` : | |");
        System.out.println("\\ \\ `-. \\_ __\\ /__ _/ .-` / /");
        System.out.println("======`-.____`-.___\\_____/___.-`____.-'======");
        System.out.println("`=---='");
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        System.out.println("佛祖保佑 永无BUG");
        System.out.println("佛曰:");
        System.out.println("写字楼里写字间，写字间里程序员；");
        System.out.println("程序人员写程序，又拿程序换酒钱。");
        System.out.println("酒醒只在网上坐，酒醉还来网下眠；");
        System.out.println("酒醉酒醒日复日，网上网下年复年。");
        System.out.println("但愿老死电脑间，不愿鞠躬老板前；");
        System.out.println("奔驰宝马贵者趣，公交自行程序员。");
        System.out.println("别人笑我忒疯癫，我笑自己命太贱；");
        System.out.println("不见满街漂亮妹，哪个归得程序员？");
        SpringApplication.run(ChatStart.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        netty.start();
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                netty.destroy();
            }
        });
    }
}
