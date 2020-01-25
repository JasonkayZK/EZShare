package top.jasonkayzk.ezshare.common.constant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.regex.Pattern;

@Disabled
public class RegexpConstantTest {


    @Test
    public void mobileRegexpTest() {
        String[] trueMobile = new String[] {"13070276321", "15507621999", "13323213332"};
        String[] falseMobile = new String[] {"1307027632", "13070276s21", "1307027 321"};

        // 创建 Pattern 对象
        Pattern pattern = Pattern.compile(RegexpConstant.MOBILE_REG);

        Assertions.assertTrue(Arrays.stream(trueMobile).allMatch(x -> pattern.matcher(x).matches()));
        Assertions.assertFalse(Arrays.stream(falseMobile).allMatch(x -> pattern.matcher(x).matches()));
    }

    @Test
    public void emailRegexpTest() {
        String[] trueMail = new String[] {"1234@qq.com", "wang@126.com", "wang123@126.com", "wang123@vip.163.com", "wang_email@outlook.com", "wang.email@gmail.com"};
        String[] falseMail = new String[] {"@mmmmmm.com", "1@1.com", "123dasf@qewfqwf.co"};

        Pattern pattern = Pattern.compile(RegexpConstant.MAIL_REX);

        Assertions.assertTrue(Arrays.stream(trueMail).allMatch(x -> pattern.matcher(x).matches()));
        Assertions.assertFalse(Arrays.stream(falseMail).allMatch(x -> pattern.matcher(x).matches()));
    }

}