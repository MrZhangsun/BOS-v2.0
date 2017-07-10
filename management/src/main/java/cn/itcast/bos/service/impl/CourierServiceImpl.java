package cn.itcast.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.itcast.bos.dao.inter.CourierRepository;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.inter.CourierServiceInter;

/**
 * 取派员模块业务层接口实现类
 * 
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月3日  下午3:26:08
 */
@Service
@Transactional
public class CourierServiceImpl implements CourierServiceInter {

        @Resource
        private CourierRepository courierRepository;
        
        /**
         * @see cn.itcast.bos.service.inter.CourierServiceInter#findAll(Specification, Pageable)
         */
        @Override
        public Page<Courier> findAll(Specification<Courier> specification, Pageable pageable) {
                return courierRepository.findAll(specification, pageable);
        }

        
        /**
         * @see cn.itcast.bos.service.inter.CourierServiceInter#saveCourier(Courier)
         */
        @Override
        public void saveCourier(Courier courier) {
                courierRepository.save(courier);
        }

        /**
         * @see cn.itcast.bos.service.inter.CourierServiceInter#findOneCourier(int)
         */
        @Override
        public Courier findOneCourier(int id) {
                return courierRepository.findOne(id);
        }

        /**
         * @see cn.itcast.bos.service.inter.CourierServiceInter#delBatch(String[])
         */
        @Override
        public void delOrRecoverBatch(String[] idStrs, String method) {
                for(String id : idStrs) {
                        if ("0".equals(method)) {
                                courierRepository.recoverBatch(Integer.parseInt(id));
                        } else if ("1".equals(method)){
                                courierRepository.delBatch(Integer.parseInt(id));
                        }
                }
        }

        /**
         * @see cn.itcast.bos.service.inter.CourierServiceInter#findAssociationCourier()
         */
        @Override
        public List<Courier> findAssociationCourier() {
                return courierRepository.findAll();
        }

}
