package cn.itcast.bos.service.order.impl;

import java.util.UUID;

import javax.annotation.Resource;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.inter.FixAreaReository;
import cn.itcast.bos.dao.order.inter.OrderRepository;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.domain.order.Order;
import cn.itcast.bos.service.order.inter.OrderServiceInter;
import cn.itcast.maven.bos_domain.Constant;

/**
 * 订单模块业务层接口实现类
 *
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月24日  上午8:05:53
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderServiceInter{

        @Resource
        private OrderRepository orderRepository;
        
        @Resource
        private FixAreaReository fixAreaReository;
        
        /**
         * @see cn.itcast.bos.service.order.inter.OrderServiceInter#saveOrder(Order)
         */
        @Override
        public void saveOrder(Order order) {
                System.out.println(order);
                /*// 根据下单地址到客户管理系统中去查询对应的定区
                String fixedAreaId = WebClient.create(Constant.CRM_MANAGEMENT_URL + "/orderService/findFixedAreaIdByAddress?address="+order.getSendAddress())
                                .accept(MediaType.APPLICATION_JSON).get(String.class);
                if (fixedAreaId != null) {
                        FixedArea fixedArea = fixAreaReository.findOne(fixedAreaId);
                        Courier courier = fixedArea.getCouriers().iterator().next();
                        if (courier != null) {
                                order.setCourier(courier);
                                order.setOrderNum(UUID.randomUUID().toString());
                                orderRepository.save(order);
                                
                        }
                }*/
        }

}
