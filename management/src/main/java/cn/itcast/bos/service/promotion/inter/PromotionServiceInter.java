package cn.itcast.bos.service.promotion.inter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.maven.bos_domain.Promotion;

/**
 * 促销模块业务层接口
 *
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月16日  下午6:09:49
 */
public interface PromotionServiceInter {

        /**
         * 保存促销信息
         * 
         * @param model 促销对象
         */
        void save(Promotion model);

        /**
         * 分页查询促销信息
         * 
         * @param pageable 分页实体
         * @return 促销数据对象的实体集合
         */
        Page<Promotion> findPromotions(Pageable pageable);

}
