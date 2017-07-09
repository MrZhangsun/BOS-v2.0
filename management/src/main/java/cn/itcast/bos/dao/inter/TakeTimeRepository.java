package cn.itcast.bos.dao.inter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.base.TakeTime;

/**
 * 排版模块持久层接口
 * 
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月9日  下午8:38:11
 */
public interface TakeTimeRepository extends JpaRepository<TakeTime, Integer>, 
                JpaSpecificationExecutor<TakeTime>{

        /**
         * 根据用户的id查询对应的排班信息
         * 
         * @param id 排班的id
         * @return 排班信息
         */
        @Query
        TakeTime findById(Integer id);

        /**
         * 删除指定的排班记录
         * 
         * @param parseInt 排班记录id
         */
        @Query
        void deleteById(int parseInt);

}
