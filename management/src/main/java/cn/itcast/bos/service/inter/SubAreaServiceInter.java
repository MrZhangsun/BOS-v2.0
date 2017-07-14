package cn.itcast.bos.service.inter;

import java.util.List;

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

        List<SubArea> findSubArea();
        
}
