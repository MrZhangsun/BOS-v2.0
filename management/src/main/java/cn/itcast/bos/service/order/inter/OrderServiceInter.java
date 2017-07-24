package cn.itcast.bos.service.order.inter;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import cn.itcast.bos.domain.order.Order;

/**
 * 订单模块业务层接口
 *
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月24日  上午8:04:32
 */
public interface OrderServiceInter {

        @Path("/saveOrder")
        @POST
        @Consumes({"application/xml", "application/xml"})
        public void saveOrder(@QueryParam("order") Order order);
        
}
