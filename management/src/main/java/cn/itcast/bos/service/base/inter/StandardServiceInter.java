package cn.itcast.bos.service.base.inter;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import cn.itcast.bos.domain.base.Standard;

/**
 * 派收标准模块业务层接口
 * 
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月1日  下午8:19:18
 */
public interface StandardServiceInter {
        
        /**
         * 保存派收标准
         * 
         * @param standard 新建的派收标准
         */
        void standardSave(Standard standard);

        /**
         * 分页查询所有的派收标准
         * 
         * @param pageable 当前页和分页单位的封装实体对象
         * @return 返回分页查询的数据结果
         */
        Page<Standard> pageQuery(Pageable pageable);

        /**
         * 修改派收标准
         * 
         * @param id 派收标准id
         * @return 派收标准
         */
        Standard rowUpdate(int id);

        /**
         * 查询所有的取派标准
         * 
         * @return 取派标准的集合
         */
        List<Standard> findStandards();
}
