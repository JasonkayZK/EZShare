package top.jasonkayzk.ezshare;

import com.spring4all.swagger.EnableSwagger2Doc;
import com.zhazhapan.config.JsonParser;
import com.zhazhapan.util.FileExecutor;
import com.zhazhapan.util.MailSender;
import com.zhazhapan.util.ReflectUtils;
import top.jasonkayzk.ezshare.config.TokenConfig;
import top.jasonkayzk.ezshare.modules.constant.ConfigConsts;
import top.jasonkayzk.ezshare.modules.constant.DefaultValues;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

/**
 * @author pantao
 */
@SpringBootApplication
@EnableSwagger2Doc
@MapperScan("top.jasonkayzk.ezshare.dao")
@EnableTransactionManagement
public class EZShareApplication {

    public static JsonParser settings = new JsonParser();

    public static List<Class<?>> controllers;

    public static Hashtable<String, Integer> tokens;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        settings.setJsonObject(FileExecutor.read(EZShareApplication.class.getResourceAsStream(DefaultValues.SETTING_PATH)));
        MailSender.config(settings.getObjectUseEval(ConfigConsts.EMAIL_CONFIG_OF_SETTINGS));
        controllers = ReflectUtils.getClasses(DefaultValues.CONTROLLER_PACKAGE);
        tokens = TokenConfig.loadToken();
        SpringApplication.run(EZShareApplication.class, args);
    }
}
