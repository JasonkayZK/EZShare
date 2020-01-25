package top.jasonkayzk.ezshare.common.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
class ApplicationUtilTest {

    @Test
    void camelToUnderscore() {
        String testStr = "testForThisFunction";
        String res = ApplicationUtil.camelToUnderscore(testStr);

        System.out.println(res);
        Assertions.assertTrue(res.chars().allMatch(x -> Character.isLowerCase(x) || '_' == x));
    }

}