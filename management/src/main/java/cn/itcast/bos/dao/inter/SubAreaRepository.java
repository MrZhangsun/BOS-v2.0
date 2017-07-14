package cn.itcast.bos.dao.inter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.itcast.bos.domain.base.SubArea;

/**
 * 分区模块数据库访问层接口
 * 
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月11日  下午8:50:46
 */
public interface SubAreaRepository extends
                JpaRepository<SubArea, String>, JpaSpecificationExecutor<SubArea>{
        
}
