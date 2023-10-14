import com.google.common.hash.Hashing;
import org.junit.jupiter.api.Test;
import top.xinsin.entity.BaseEntity;

import java.io.IOException;
import java.io.InputStream;

public class SHA3512Test {
    @Test
    public void onTest(){
        try(InputStream is = BaseEntity.class.getClassLoader().getResourceAsStream("default_portraits.svg")) {
            if (is != null) {
                byte[] bytes = is.readAllBytes();
                System.out.println(bytes.length);
                System.out.println(Hashing.sha512().hashBytes(bytes));
            }else {
                throw new RuntimeException("创建默认头像失败，无法获取默认头像！");
            }
        } catch (IOException e) {
            throw new RuntimeException("创建默认头像失败", e);
        }
    }
}
