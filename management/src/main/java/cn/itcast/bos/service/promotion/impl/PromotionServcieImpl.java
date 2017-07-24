package cn.itcast.bos.service.promotion.impl;

import java.util.Date;

import javax.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.itcast.bos.dao.promotion.inter.PromotionRepository;
import cn.itcast.bos.service.promotion.inter.PromotionServiceInter;
import cn.itcast.maven.bos_domain.PageBean;
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

        /**
         * @see cn.itcast.bos.service.promotion.inter.PromotionServiceInter#findPromotionByPage(int, int)
         */
        @Override
        public PageBean<Promotion> findPromotionByPage(int page, int rows) {
                Pageable pageable = new PageRequest(page -1, rows);
                Page<Promotion> promotions = promotionRepository.findAll(pageable);
                PageBean<Promotion> pageBean = new PageBean<Promotion>();
                pageBean.setTotalCount((int) promotions.getTotalElements());
                pageBean.setPageContent(promotions.getContent());
                return pageBean;
        }

        /**
         * @see cn.itcast.bos.service.promotion.inter.PromotionServiceInter#findPromotionById(Integer)
         */
        @Override
        public Promotion findPromotionById(Integer id) {
                return promotionRepository.findOne(id);
        }

        /**
         * @see cn.itcast.bos.service.promotion.inter.PromotionServiceInter#updateStatus(Date)
         */
        @Override
        public void updateStatus(Date now) {
                System.out.println("促销日期更新");
                promotionRepository.updateStatus(now);
        }

        /**
         * @see cn.itcast.bos.service.promotion.inter.PromotionServiceInter#deletePromotions(String)
         */
        @Override
        public void deletePromotions(String ids) {
                if (ids != null) {
                        String[] split = ids.split("-");
                        for(String id : split) {
                                promotionRepository.deletePromotion(Integer.parseInt(id));
                        }
                }
        }
}
