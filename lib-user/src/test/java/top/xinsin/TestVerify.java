package top.xinsin;

import org.apache.commons.codec.cli.Digest;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

public class TestVerify {
    @Test
    public void test(){
        String username = "DongYifeng";
        String username1 = "DongYiFeng";
        System.out.println(DigestUtils.sha512Hex(DigestUtils.md5Hex(username)).equals(DigestUtils.sha512Hex(DigestUtils.md5Hex(username1))));
    }
}
