package top.jasonkayzk.ezshare;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 程序启动类
 *
 * @author zk
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
public class EzShareApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(EzShareApplication.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }

}
