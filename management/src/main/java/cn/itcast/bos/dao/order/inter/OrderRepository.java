package cn.itcast.bos.dao.order.inter;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import cn.itcast.bos.domain.order.Order;

/**
 * 订单模块数据库访问层接口实现类
 *
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月24日  下午1:41:20
 */
public interface OrderRepository extends JpaRepository<Order, Integer>, Specification<Order>{

}
