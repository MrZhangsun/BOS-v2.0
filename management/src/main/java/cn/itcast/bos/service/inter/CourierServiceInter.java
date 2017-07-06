package cn.itcast.bos.service.inter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.Courier;

/**
 * 取派员模块业务层接口
 * 
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月3日  下午3:25:18
 */
public interface CourierServiceInter {

        /**
         * 分页查询所有的取派员
         * 
         * @param pageable 分页单位和当前页的封装对象
         * @return 当前页所显示的所有派件员的集合
         */
        Page<Courier> findAll(Specification<Courier> specification, Pageable pageable);       

        /**
         * 保存取派员信息
         * 
         * @param courier 取派员信息
         */
        void saveCourier(Courier courier);

        /**
         * 根据id查询指定的取派员
         * 
         * @param id 取派员的id
         * @return 取派员的信息
         */
        Courier findOneCourier(int id);

        /**
         * 作废取派员信息
         * 
         * @param idStrs 取派员的id数组
         */
        void delOrRecoverBatch(String[] idStrs, String method);



}
