package cn.wzpmc;

import lombok.SneakyThrows;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * @author wzp
 * Created On 2022/5/14
 * @version 1.0
 */
public class ChatStart {
    public static SqlSessionFactory factory;
    @SneakyThrows
    public static void main(String[] args) {
        Netty netty = new Netty();
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        factory = new SqlSessionFactoryBuilder().build(inputStream);
        netty.start();
    }
}
