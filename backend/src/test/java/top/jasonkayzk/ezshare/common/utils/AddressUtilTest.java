package top.jasonkayzk.ezshare.common.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

@Disabled
class AddressUtilTest {

    @Test
    void getCityInfo() {
        String[] ips = new String[] {
                "127.0.0.1",
                "192.168.0.1",
                "123.15.163.242",
                "111.206.222.68",
                "37.17.184.0",
                "61.134.201.0",
                "61.141.205.0",
                "223.223.216.0"
        };

        var res = new ArrayList<String>(16);

        Arrays.stream(ips).forEach(ip -> res.add(AddressUtil.getCityInfo(ip)));

        res.forEach(System.out::println);

        Assertions.assertTrue(res.stream().allMatch(Objects::nonNull));
    }

}