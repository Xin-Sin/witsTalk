package cn.wzpmc;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @Author xinxin
 * @Date 2021/12/13 10:58
 * @Version 1.0
 */
public class testMD5 {
    @Test
    public void MD5(){
        String admin = DigestUtils.md5Hex("admin");
        System.out.println("admin = " + admin);
        String bytes = DigestUtils.sha512Hex(admin);
        System.out.println("s = " + bytes);
    }
}
//edbd881f1ee2f76ba0bd70fd184f87711be991a0401fd07ccd4b199665f00761afc91731d8d8ba6cbb188b2ed5bfb465b9f3d30231eb0430b9f90fe91d136648
//22b12a761a4cc5fb8b3633b2bf728ce7ffc1db96593b9fa3adbdb6c88df1f974cd306ef4d6217f5406781dcf7d165822e3a8d2cd2bf8eb425330def115eb9920
//21232f297a57a5a743894a0e4a801fc3