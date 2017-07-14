package cn.itcast.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.inter.SubAreaRepository;
import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.service.inter.SubAreaServiceInter;

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
         * @see cn.itcast.bos.service.inter.SubAreaServiceInter#importSubArea(List)
         * @param list
         */
        @Override
        public void importSubArea(List<SubArea> list) {
                subAreaRepository.save(list);
        }

        @Override
        public void deleteArea(String[] del) {
                // TODO Auto-generated method stub
                
        }

        @Override
        public List<SubArea> findSubArea() {
                // TODO Auto-generated method stub
                return null;
        }

}
