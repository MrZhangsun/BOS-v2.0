package cn.itcast.bos.quartz;

import java.util.Date;
import javax.annotation.Resource;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import cn.itcast.bos.service.promotion.inter.PromotionServiceInter;

/**
 * 促销模块定时更新促销状态
 *
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月20日  上午10:52:01
 */
public class PromotionJob implements Job{

        @Resource
        private PromotionServiceInter promotionServiceinter;
        
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
                promotionServiceinter.updateStatus(new Date());
        }

}
