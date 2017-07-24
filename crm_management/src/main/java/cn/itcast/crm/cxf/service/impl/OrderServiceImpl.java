package cn.itcast.crm.cxf.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.crm.cxf.dao.inter.OrderRepository;
import cn.itcast.crm.cxf.service.inter.OrderServiceInter;

/**
 * 订单模块业务层接口实现类
 *
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月24日  下午6:38:13
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderServiceInter {

        @Resource
        private OrderRepository orderRepository;
        /**
         * @see cn.itcast.crm.cxf.service.inter.OrderServiceInter#findFixedAreaIdByAddress(String)
         */
        @Override
        public String findFixedAreaIdByAddress(String address) {
                return orderRepository.findFixedAreaIdByAddress(address);
        }

}
