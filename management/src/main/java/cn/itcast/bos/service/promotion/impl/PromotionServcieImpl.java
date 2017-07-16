package cn.itcast.bos.service.promotion.impl;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.promotion.impl.PromotionRepository;
import cn.itcast.bos.service.promotion.inter.PromotionServiceInter;
import cn.itcast.maven.bos_domain.Promotion;

/**
 * 促销模块业务层接口实现类
 *
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月16日  下午6:11:11
 */
@Service
@Transactional
public class PromotionServcieImpl implements PromotionServiceInter {

        @Resource
        private PromotionRepository promotionRepository;

        /**
         * @see cn.itcast.bos.service.promotion.inter.PromotionServiceInter#save(Promotion)
         */
        @Override
        public void save(Promotion model) {
                promotionRepository.save(model);
        }

        /**
         * @see cn.itcast.bos.service.promotion.inter.PromotionServiceInter#findPromotions(Pageable)
         */
        @Override
        public Page<Promotion> findPromotions(Pageable pageable) {
                return promotionRepository.findAll(pageable);
        }
}
