package cn.itcast.bos.service.base.inter;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.bos.domain.base.SubArea;

/**
 * 分区模块业务层接口
 * 
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月11日  下午8:46:06
 */
public interface SubAreaServiceInter {

        /**
         * 导入分区数据
         * 
         * @param list 分区数据
         */
        void importSubArea(List<SubArea> list);

        void deleteArea(String[] del);

        /**
         * 分页查询所有的分区信息
         * @param pageable 
         * 
         * @return 分区数据
         */
        Page<SubArea> findSubArea(Pageable pageable);

        /**
         * 导出所有的分页数据
         * 
         * @return 导出分区数据
         */
        List<SubArea> findAllSubAreas();
        
}
