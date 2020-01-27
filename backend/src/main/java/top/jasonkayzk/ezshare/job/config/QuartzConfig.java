package top.jasonkayzk.ezshare.job.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * 定时任务配置
 *
 * @author zk
 */
@Configuration
public class QuartzConfig {

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource) {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        factoryBean.setDataSource(dataSource);

        // quartz参数
        Properties prop = new Properties();
        // 调度标识名: 集群中每一个实例都必须使用相同的名称(区分特定的调度器实例)
        prop.put("org.quartz.scheduler.instanceName", "DefaultQuartzScheduler");
        // ID设置为自动获取: 每一个必须不同(所有调度器实例中是唯一的)
        prop.put("org.quartz.scheduler.instanceId", "AUTO");

        // 线程池配置
        // ThreadPool实现的类名
        prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        // 线程数量
        prop.put("org.quartz.threadPool.threadCount", "20");
        // 线程优先级
        // threadPriority属性
        //   最大值是常量 java.lang.Thread.MAX_PRIORITY，等于10
        //   最小值为常量 java.lang.Thread.MIN_PRIORITY，为1
        prop.put("org.quartz.threadPool.threadPriority", "5");

        // JobStore配置
        // 数据保存方式为持久化
        prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        prop.put("org.quartz.jobStore.useProperties", "false");

        // 集群配置
        // 加入集群: true为集群, false不是集群
        prop.put("org.quartz.jobStore.isClustered", "true");
        // 调度实例失效的检查时间间隔
        prop.put("org.quartz.jobStore.clusterCheckinInterval", "15000");
        // JobStore处理未按时触发的Job数量
        prop.put("org.quartz.jobStore.maxMisfiresToHandleAtATime", "5");
        // 容许的最大作业延长时间
        prop.put("org.quartz.jobStore.misfireThreshold", "12000");
        // 表的前缀
        prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
        factoryBean.setQuartzProperties(prop);

        factoryBean.setSchedulerName("DefaultQuartzScheduler");

        // 延时启动
        factoryBean.setStartupDelay(1);
        factoryBean.setApplicationContextSchedulerContextKey("applicationContextKey");

        // 可选，QuartzScheduler
        // 启动时更新己存在的 Job
        factoryBean.setOverwriteExistingJobs(true);

        // 设置自动启动，默认为 true
        factoryBean.setAutoStartup(true);

        return factoryBean;
    }

}
