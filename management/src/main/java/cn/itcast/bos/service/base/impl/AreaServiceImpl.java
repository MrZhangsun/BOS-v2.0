package cn.itcast.bos.service.base.impl;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.inter.AreaRepository;
import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.base.inter.AreaServiceInter;

/**
 * 区域模块业务层接口实现类
 * 
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月4日  下午11:37:07
 */
@Service
@Transactional
public class AreaServiceImpl implements AreaServiceInter {

        @Resource
        private AreaRepository areaRepository;
        
        /**
         * @see cn.itcast.bos.service.base.inter.AreaServiceInter#saveAreaInfo(ArrayList)
         */
        @Override
        public void saveAreaInfo(ArrayList<Area> list) {
                areaRepository.save(list);
        }
        
        /**
         * @see cn.itcast.bos.service.base.inter.AreaServiceInter#pageQueryArea(Specification, Pageable)
         */
        @Override
        public Page<Area> pageQueryArea(Specification<Area> specification, Pageable pageable) {
                return areaRepository.findAll(specification, pageable);
        }

        /**
         * @see cn.itcast.bos.service.base.inter.AreaServiceInter#saveOrUpdateArea(Area)
         */
        @Override
        public void saveOrUpdateArea(Area area) {
                areaRepository.save(area);
        }

        /**
         * @see cn.itcast.bos.service.base.inter.AreaServiceInter#findOne(String)
         */
        @Override
        public Area findOne(String id) {
                return areaRepository.findById(id);
        }

        /**
         * @see cn.itcast.bos.service.base.inter.AreaServiceInter#deleteArea(String[])
         */
        @Override
        public void deleteArea(String[] del) {
                if (del != null) {
                        for (String id : del) {
                                areaRepository.deleteById(id);
                        }
                }
        }




}
