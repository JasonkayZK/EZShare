package top.jasonkayzk.ezshare.common.authentication;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Disabled
@SpringBootTest
public class AuthenticationConfigTest {

    private static long expireTime;

    @Value("${shiro.anonUrl}")
    private String anonUrl;

    @Value("${shiro.jwtTimeOut}")
    public void setExpireTime(long expireTime) {
        AuthenticationConfigTest.expireTime = expireTime;
    }

    @Test
    public void propertyTest() {
        System.out.println(expireTime);
        System.out.println(anonUrl);

        Assertions.assertNotNull(anonUrl);
        Assertions.assertTrue(expireTime > 1000);
    }

}