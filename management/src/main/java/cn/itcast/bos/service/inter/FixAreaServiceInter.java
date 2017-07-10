package cn.itcast.bos.service.inter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.FixedArea;

/**
 * 定区的业务层接口
 * 
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月5日  下午7:52:43
 */
public interface FixAreaServiceInter {

        /**
         * 添加或者修改定区的信息
         * 
         * @param model 定区信息
         */
        void addOrUpdateFixArea(FixedArea model);

        /**
         * 根据id查询要修改的定区信息
         * 
         * @param id 定区的id
         * @return 
         */
        FixedArea updateFixedArea(String id);

        /**
         * 分页查询所有的定区信息
         * 
         * @param specification 查询条件
         * @param pageable 分页信息
         * @return 查询到的分页数据
         */
        Page<FixedArea> fixedAreaQuery(Specification<FixedArea> specification, Pageable pageable);

        /**
         * 删除指定的数据库区域记录
         * 
         * @param ids 删除记录的id编号
         */
        void deleteFixedArea(String[] ids);

        /**
         * 将取派员和排班时间关联到定区
         * 
         * @param model 定区
         * @param courierId 取派员id
         * @param takeTimeId 排班时间id
         */
        void associateToFixedArea(FixedArea model, Integer courierId, Integer takeTimeId);

}
