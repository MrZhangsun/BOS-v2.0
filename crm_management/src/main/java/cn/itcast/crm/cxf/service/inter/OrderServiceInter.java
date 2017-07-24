package cn.itcast.crm.cxf.service.inter;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 * 订单模块业务层接口
 *
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月24日  下午6:34:28
 */
public interface OrderServiceInter {

        @GET
        @Path("/findFixedAreaIdByAddress")
        @Consumes({"application/xml","application/json"})
        @Produces({"application/xml", "application/json"})
        public String findFixedAreaIdByAddress(@QueryParam("address") String address);
}
