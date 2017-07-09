package cn.itcast.crm.cxf.service.inter;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import cn.itcast.crm.domain.Customer;

/**
 * 服务器端资源接口
 * 
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月7日  下午8:26:15
 */
//@Path(value="service")
@Consumes("*/*")
public interface CustomerServiceInter {

        /**
         *  查找没有关联定区的客户
         * 
         *  @return 没有关联客户的集合
         */
        @GET
        @Path("/findNoAssociationCustomers")
        @Produces("application/json")
        public List<Customer> findNoAssociationCustomers();
        
        /**
         * 查询所有已经关联定区的客户
         * 
         * @return 所有已经关联定区的客户的集合
         */
        @GET
        @Path("/findAssociationCustomers/{id}")
        @Produces("application/json")
        public List<Customer> findAssociationCustomers(@PathParam("id") String id);
        
        /**
         * 将用户关联到指定的定区上
         * 
         * @param CustomerIds 指定要关联的客户id的字符串
         * @param fixId 定区id
         * @param customerIds 客户的id串
         */
        @PUT
        @Path("/associateToFixedArea")
        public void associateToFixedArea(@QueryParam("customerIds") String customerIds,
                        @QueryParam("fixId") String fixId);
}
