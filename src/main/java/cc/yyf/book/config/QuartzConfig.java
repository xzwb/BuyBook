package cc.yyf.book.config;

import cc.yyf.book.job.OrderJob;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

@Configuration
public class QuartzConfig {
    /**
     * 创建job对象
     */
    @Bean
    public JobDetailFactoryBean jobDetailFactoryBean() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(OrderJob.class);
        return factoryBean;
    }

    /**
     * 创建Trigger对象
     */
    @Bean
    public SimpleTriggerFactoryBean simpleTriggerFactoryBean() {
        SimpleTriggerFactoryBean simpleTriggerFactoryBean = new SimpleTriggerFactoryBean();
        simpleTriggerFactoryBean.setJobDetail(jobDetailFactoryBean().getObject());
        // 每60秒执行一次
        simpleTriggerFactoryBean.setRepeatInterval(60*1000);
        return simpleTriggerFactoryBean;
    }

    /**
     * 创建Scheduler对象
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(MyAdaptableJobFactory myAdaptableJobFactory) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setTriggers(simpleTriggerFactoryBean().getObject());
        schedulerFactoryBean.setJobFactory(myAdaptableJobFactory);
        return schedulerFactoryBean;
    }
}
