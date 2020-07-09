package cc.yyf.book.config;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

@Component
public class MyAdaptableJobFactory extends AdaptableJobFactory {

    /**
     * 可以将一个对象添加到springIoc容器中
     */
    @Autowired
    private AutowireCapableBeanFactory autowireCapableBeanFactory;

    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        Object object = super.createJobInstance(bundle);
        // 将obj对象添加到springIoc容器中，并完成注入
        this.autowireCapableBeanFactory.autowireBean(object);
        return object;
    }
}