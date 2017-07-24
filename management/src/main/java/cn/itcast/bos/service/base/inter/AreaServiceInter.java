package cn.itcast.bos.service.base.inter;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.Area;

/**
 * 区域模块业务层接口
 * 
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月4日  下午11:36:30
 */
public interface AreaServiceInter {

        /**
         * 保存从文件中解析的内容
         * 
         * @param list 信息集合
         */
        void saveAreaInfo(ArrayList<Area> list);

        /**
         * 条件分页查询区域信息
         * 
         * @param specification 查询条件
         * @param pageable 分页
         * @return 
         */
        Page<Area> pageQueryArea(Specification<Area> specification, Pageable pageable);

        /**
         * 保存或者修改区域信息
         * 
         * @param area 区域信息
         */
        void saveOrUpdateArea(Area area);

        /**
         * 根据id查询指定区域的信息
         * 
         * @param id 区域id
         * @return 区域对象
         */
        Area findOne(String id);

        /**
         * 删除指定的区域
         * 
         * @param del 区域id的数组
         */
        void deleteArea(String[] del);

}
