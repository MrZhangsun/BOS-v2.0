package cn.itcast.crm.cxf.dao.inter;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.order.Order;

/**
 * 订单模块数据库访问层接口
 *
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月24日  下午7:04:20
 */
public interface OrderRepository extends JpaRepository<Order, Integer>, Specification<Order>{

        
        String findFixedAreaIdByAddress(String address);


}
