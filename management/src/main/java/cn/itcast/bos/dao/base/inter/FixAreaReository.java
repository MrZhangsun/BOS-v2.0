package cn.itcast.bos.dao.base.inter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.base.FixedArea;

/**
 * 定区模块数据库访问层接口
 * 
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月5日  下午7:56:46
 */
public interface FixAreaReository extends JpaRepository<FixedArea, String>, JpaSpecificationExecutor<FixedArea> {

        /**
         * 根据定区的id查询定区信息
         * 
         * @param id 定区的id
         * @return 定区信息
         */
        @Query
        FixedArea findById(String id);
        
        /**
         * 根据id删除指定的定区
         * 
         * @param id 定区的id
         */
        @Query
        void deleteById(String id);


}
