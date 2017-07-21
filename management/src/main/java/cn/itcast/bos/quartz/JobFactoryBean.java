package cn.itcast.bos.quartz;

import javax.annotation.Resource;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

/**
 * 任务工厂
 *
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月20日  上午11:13:05
 */
@Component("jobFactoryBean")
public class JobFactoryBean extends AdaptableJobFactory {

        @Resource
        private AutowireCapableBeanFactory autowireCapableBeanFactory;
        
        @Override
        protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
                Object jobInstance = super.createJobInstance(bundle);
                autowireCapableBeanFactory.autowireBean(jobInstance);
                return jobInstance;
        }

}
