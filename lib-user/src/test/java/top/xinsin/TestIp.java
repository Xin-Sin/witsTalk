package top.xinsin;

import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author xinsin
 * Created On 2022/12/5 13:39
 * @version 1.0
 */
public class TestIp {
    @Test
    public void testIp01() throws UnknownHostException {
        System.out.println(InetAddress.getLocalHost().getHostAddress());
    }
}
