package cn.itcast.bos.dao.promotion.inter;

import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import cn.itcast.maven.bos_domain.Promotion;

/**
 * 促销模块数据库访问层接口
 *
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月16日  下午6:12:34
 */
public interface PromotionRepository extends 
                JpaRepository<Promotion, Integer>, JpaSpecificationExecutor<Promotion> {

        /**
         * 定时更新促销状态
         * 
         * @param now 当前时间
         */
        @Query(value = "update Promotion set status = '0' where endDate < ? and status = '1' ", nativeQuery = false)
        @Modifying
        void updateStatus(Date now);


}
