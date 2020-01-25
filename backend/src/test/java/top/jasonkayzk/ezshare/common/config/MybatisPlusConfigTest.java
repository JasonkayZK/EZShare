package top.jasonkayzk.ezshare.common.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

@Disabled
@SpringBootTest
public class MybatisPlusConfigTest {

    @Autowired
    ApplicationContext context;

    @Test
    public void configTest() {
        Environment environment = context.getEnvironment();
        var typeAliasesPackage = environment.getProperty("mybatis-plus.type-aliases-package");
        System.out.println(typeAliasesPackage);
        Assertions.assertNotNull(typeAliasesPackage);

        var mapperLocations = environment.getProperty("mybatis-plus.mapper-locations");
        System.out.println(mapperLocations);
        Assertions.assertNotNull(mapperLocations);
    }

}