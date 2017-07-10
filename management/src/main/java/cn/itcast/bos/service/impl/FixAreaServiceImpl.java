package cn.itcast.bos.service.impl;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.inter.FixAreaReository;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.service.inter.FixAreaServiceInter;

/**
 * 定区的业务层接口实现类
 * 
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月5日  下午7:55:23
 */
 @Service
 @Transactional
public class FixAreaServiceImpl implements FixAreaServiceInter {

         @Resource
         private FixAreaReository fixAreaReository;
         
         /**
          * @see cn.itcast.bos.service.inter.FixAreaServiceInter#addOrUpdateFixArea(FixedArea)
          */
        @Override
        public void addOrUpdateFixArea(FixedArea model) {
                fixAreaReository.save(model);
        }

        /**
         * @see cn.itcast.bos.service.inter.FixAreaServiceInter#updateFixedArea(String)
         */
        @Override
        public FixedArea updateFixedArea(String id) {
                return fixAreaReository.findById(id);
        }

        /**
         * @see cn.itcast.bos.service.inter.FixAreaServiceInter#fixedAreaQuery(Specification, Pageable)
         */
        @Override
        public Page<FixedArea> fixedAreaQuery(Specification<FixedArea> specification, Pageable pageable) {
                return fixAreaReository.findAll(specification, pageable);
        }

        /**
         * @see cn.itcast.bos.service.inter.FixAreaServiceInter#deleteFixedArea(String[])
         */
        @Override
        public void deleteFixedArea(String[] ids) {
                if (ids.length > 0) {
                        for (String id : ids) {
                                fixAreaReository.deleteById(id);
                        }
                }
        }

        /**
         * @see cn.itcast.bos.service.inter.FixAreaServiceInter#associateToFixedArea(FixedArea, Integer, Integer)
         */
        @Override
        public void associateToFixedArea(FixedArea model, Integer courierId, Integer takeTimeId) {
                fixAreaReository.findOne(id);
        }

}
