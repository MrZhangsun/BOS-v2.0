package cn.itcast.bos.service.promotion.inter;

import java.util.Date;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import cn.itcast.maven.bos_domain.PageBean;
import cn.itcast.maven.bos_domain.Promotion;

/**
 * 促销模块业务层接口
 *
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月16日  下午6:09:49
 */
public interface PromotionServiceInter {

        /**
         * 保存促销信息
         * 
         * @param model 促销对象
         */
        void save(Promotion model);

        /**
         * 分页查询促销信息
         * 
         * @param pageable 分页实体
         * @return 促销数据对象的实体集合
         */
        Page<Promotion> findPromotions(Pageable pageable);

        /**
         * 分页查询 
         */
        @GET
        @Path("/findPromotionByPage")
        @Consumes({"application/xml", "application/json"})
        @Produces({"application/xml", "application/json"})
        PageBean<Promotion> findPromotionByPage(@QueryParam("page")int page, @QueryParam("rows")int rows);
        
        /**
         *  根据id查询对应的促销商品
         *  
         *  @return promotion 促销商品
         */
        @GET
        @Path("/findPromotionById/{id}")
        @Consumes({"application/xml", "application/json"})
        @Produces({"application/xml", "application/json"})
        Promotion findPromotionById(@PathParam("id") Integer id);

        /**
         * 定时更新商品的促销状态
         * 
         * @param date 当前时间
         */
        void updateStatus(Date now);

        /**
         * 逻辑删除促销商品
         * 
         * @param ids
         */
        void deletePromotions(String ids);
}
