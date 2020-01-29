package top.jasonkayzk.ezshare.common.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MD5UtilTest {

    @Test
    void encrypt() {
        String encrypt = MD5Util.encrypt("123456");
        System.out.println(encrypt);
        Assertions.assertNotNull(encrypt);
        Assertions.assertEquals(encrypt.length(), 32);
    }

    @Test
    void testEncrypt() {
        String encrypt = MD5Util.encrypt("jasonkay", "123456");
        System.out.println(encrypt);
        Assertions.assertNotNull(encrypt);
        Assertions.assertEquals(encrypt.length(), 32);
    }
}