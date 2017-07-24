package cn.itcast.bos.service.base.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.inter.SubAreaRepository;
import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.service.base.inter.SubAreaServiceInter;

/**
 * 分区模块业务层接口实现类
 * 
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月11日  下午8:48:36
 */
@Service
@Transactional
public class SubAreaServiceImpl implements SubAreaServiceInter {

        @Resource
        private SubAreaRepository subAreaRepository;
        
        /**
         * @see cn.itcast.bos.service.base.inter.SubAreaServiceInter#importSubArea(List)
         * @param list
         */
        @Override
        public void importSubArea(List<SubArea> list) {
                subAreaRepository.save(list);
        }

        /**
         * @see cn.itcast.bos.service.base.inter.SubAreaServiceInter#deleteArea(String[])
         */
        @Override
        public void deleteArea(String[] del) {
                if (del != null) {
                        for (String id :del) {
                                subAreaRepository.delete(id);
                        }
                }
        }

        /**
         * @see cn.itcast.bos.service.base.inter.SubAreaServiceInter#findSubArea(Pageable)
         */
        @Override
        public Page<SubArea> findSubArea(Pageable pageable) {
                return subAreaRepository.findAll(pageable);
        }

        /**
         * @see cn.itcast.bos.service.base.inter.SubAreaServiceInter#findAllSubAreas()
         */
        @Override
        public List<SubArea> findAllSubAreas() {
                return subAreaRepository.findAll();
        }

}
