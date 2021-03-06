package top.jasonkayzk.ezshare.common.generator;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;

/**
 * Mybatis-Plus提供的代码生成器
 *
 * 可以快速生成 Entity、Mapper、Mapper XML、Service、Controller等各个模块的代码
 *
 * @link https://mp.baomidou.com/guide/generator.html
 *
 * @author zk
 */
public class CodeGenerator {

    /**
     * 数据库 URL
     */
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/ezshare?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    /**
     * 数据库驱动
     */
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

    /**
     * 数据库用户名
     */
    private static final String USERNAME = "root";

    /**
     * 数据库密码
     */
    private static final String PASSWORD = "123456";

    /**
     * Author值
     */
    private static final String AUTHOR = "Jasonkay";

    /**
     * 包的基础路径
     */
    private static final String BASE_PACKAGE_URL = "top.jasonkayzk.ezshare";

    /**
     * 模板路径
     */
    private static final String XML_MAPPER_TEMPLATE_PATH = "generator/templates/mapper.xml";

    /**
     * 表前缀(去掉)
     */
    private static final String TABLE_PREFIX = "t_";

    /**
     * mapper文件模板
     */
    private static final String MAPPER_TEMPLATE_PATH = "generator/templates/mapper.java";

    /**
     * entity文件模板
     */
    private static final String ENTITY_TEMPLATE_PATH = "generator/templates/entity.java";

    /**
     * service文件模板
     */
    private static final String SERVICE_TEMPLATE_PATH = "generator/templates/service.java";

    /**
     * serviceImpl文件模板
     */
    private static final String SERVICE_IMPL_TEMPLATE_PATH = "generator/templates/serviceImpl.java";

    /**
     * controller文件模板
     */
    private static final String CONTROLLER_TEMPLATE_PATH = "generator/templates/controller.java";

    public static void main(String[] args) {
        AutoGenerator generator = new AutoGenerator();

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        globalConfig.setOutputDir(projectPath + "/src/main/java");
        globalConfig.setAuthor(AUTHOR);
        globalConfig.setOpen(false);
        globalConfig.setFileOverride(false);
        generator.setGlobalConfig(globalConfig);

        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl(URL);
        dataSourceConfig.setDriverName(DRIVER_NAME);
        dataSourceConfig.setUsername(USERNAME);
        dataSourceConfig.setPassword(PASSWORD);
        generator.setDataSource(dataSourceConfig);

        // 包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setModuleName("gen");
        packageConfig.setParent(BASE_PACKAGE_URL);
        generator.setPackageInfo(packageConfig);

        // 配置自定义代码模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(XML_MAPPER_TEMPLATE_PATH);
        templateConfig.setMapper(MAPPER_TEMPLATE_PATH);
        templateConfig.setEntity(ENTITY_TEMPLATE_PATH);
        templateConfig.setService(SERVICE_TEMPLATE_PATH);
        templateConfig.setServiceImpl(SERVICE_IMPL_TEMPLATE_PATH);
        templateConfig.setController(CONTROLLER_TEMPLATE_PATH);
        generator.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setInclude(scanner());
        // 加入则不生成id列
        // strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);
        // 去掉表中前缀
        strategy.setTablePrefix(TABLE_PREFIX);
        generator.setStrategy(strategy);
        generator.setTemplateEngine(new FreemarkerTemplateEngine());
        generator.execute();
    }


    private static String[] scanner() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(("请输入表名(多个表使用空格分开)" + "："));
        // t_dict t_file t_file_category t_file_download_log t_job t_job_log t_log t_login_log t_menu t_role t_role_menu t_user t_user_config t_user_role t_file_auth t_test
        if (scanner.hasNextLine()) {
            String ipt = scanner.nextLine();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt.split("\\s");
            }
        }
        throw new MybatisPlusException("请输入正确的" + "表名" + "！");
    }

}
