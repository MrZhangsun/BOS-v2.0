package cn.itcast.bos.service.base.inter;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.bos.domain.base.TakeTime;

/**
 * 排班模块业务层接口
 * 
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月9日  下午8:23:41
 */
public interface TakeTimeServiceInter {
        
        /**
         * 查询所有的排班记录
         * 
         * @param pageable 当前页和分页单位的封装对象
         * @return 所有的排班记录
         */
        Page<TakeTime> getTakeTimeTable(Pageable pageable);

        /**
         * 保存或者修改排班记录
         * 
         * @param model 排班实体模型
         */
        void save(TakeTime model);

        /**
         * 根据排班id查询对应的排班信息
         * 
         * @param id 排班的id
         * @return 
         */
        TakeTime searchTakeTime(Integer id);

        /**
         * 删除指定的排班记录
         * 
         * @param ids 要删除的排班记录id串
         */
        void deleteTakeTimes(String ids);

        /**
         * 关联所有的排版时间
         * 
         * @return 所有的排班时间
         */
        List<TakeTime> findAll();
        
}
