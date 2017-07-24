package cn.itcast.bos.service.base.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.inter.StandardRepository;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.inter.StandardServiceInter;

/**
 * 派收标准业务层接口实现类
 * 
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月1日  下午8:22:36
 */
@Service
@Transactional
public class StandardServiceImpl implements StandardServiceInter {

        @Resource
        private StandardRepository standardRepository;
        /**
         * @see cn.itcast.bos.service.base.inter.StandardServiceInter#standardSave(Standard)
         */
        @Override
        public void standardSave(Standard standard) {
                standardRepository.save(standard);
        }
        
        /**
         * @see cn.itcast.bos.service.base.inter.StandardServiceInter#pageQuery(Pageable)
         */
        @Override
        public Page<Standard> pageQuery(Pageable pageable) {
                return standardRepository.findAll(pageable);
        }

        /**
         * @see cn.itcast.bos.service.base.inter.StandardServiceInter#rowUpdate(int)
         */
        @Override
        public Standard rowUpdate(int id) {
                return standardRepository.findOne(id);
        }

        /**
         * @see cn.itcast.bos.service.base.inter.StandardServiceInter#findStandards()
         */
        @Override
        public List<Standard> findStandards() {
                return standardRepository.findAll();
        }

}
