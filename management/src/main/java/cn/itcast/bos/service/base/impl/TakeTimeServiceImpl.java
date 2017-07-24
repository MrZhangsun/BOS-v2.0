package cn.itcast.bos.service.base.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.inter.TakeTimeRepository;
import cn.itcast.bos.domain.base.TakeTime;
import cn.itcast.bos.service.base.inter.TakeTimeServiceInter;

/**
 * 排班模块业务层接口
 * 
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月9日  下午8:34:06
 */
@Service
@Transactional
public class TakeTimeServiceImpl implements TakeTimeServiceInter {

        @Resource
        private TakeTimeRepository takeTimeRepository;
        /**
         * @see cn.itcast.bos.service.base.inter.TakeTimeServiceInter#getTakeTimeTable(Pageable)
         */
        @Override
        public Page<TakeTime> getTakeTimeTable(Pageable pageable) {
                return takeTimeRepository.findAll(pageable);
        }
        
        /**
         * @see cn.itcast.bos.service.base.inter.TakeTimeServiceInter#save(TakeTime)
         */
        @Override
        public void save(TakeTime model) {
                takeTimeRepository.save(model);
        }

        /**
         * @see cn.itcast.bos.service.base.inter.TakeTimeServiceInter#searchTakeTime(Integer)
         */
        @Override
        public TakeTime searchTakeTime(Integer id) {
                return takeTimeRepository.findById(id);
        }

        /**
         * @see cn.itcast.bos.service.base.inter.TakeTimeServiceInter#deleteTakeTimes(String)
         */
        @Override
        public void deleteTakeTimes(String ids) {
                String[] split = ids.split("-");
                for (String id:split) {
                        takeTimeRepository.deleteById(Integer.parseInt(id));
                }
        }
        
        /**
         * @see cn.itcast.bos.service.base.inter.TakeTimeServiceInter#findAll()
         */
        @Override
        public List<TakeTime> findAll() {
                return takeTimeRepository.findAll();
        }
}
