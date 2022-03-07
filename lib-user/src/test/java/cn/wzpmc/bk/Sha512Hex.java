package cn.wzpmc.bk;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

public class Sha512Hex {
    @Test
    public void test(){
        System.out.println(DigestUtils.sha512Hex("098f6bcd4621d373cade4e832627b4f6"));
    }
}
