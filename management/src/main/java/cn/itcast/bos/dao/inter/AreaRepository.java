package cn.itcast.bos.dao.inter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import cn.itcast.bos.domain.base.Area;

/**
 * 区域模块持久层接口
 * 
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月4日  下午11:38:11
 */
public interface AreaRepository extends JpaRepository<Area, Integer>, JpaSpecificationExecutor<Area> {

        /**
         * 根据区域编码id查询区域对象
         * 
         * @param id 区域编码id
         * @return 区域对象
         */
        Area findById(String id);

        /**
         * 根据区域的id删除指定的区域
         * 
         * @param id 区域id
         */
        void deleteById(String id);

}
