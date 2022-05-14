package cn.wzpmc.bk;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

public class Sha512Hex {
    @Test
    public void test(){
        System.out.println(DigestUtils.sha512Hex("c3da775a72ebe17f73481ca961ce705d"));
    }
}
