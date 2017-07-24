package cn.itcast.bos.dao.base.inter;

import org.springframework.data.jpa.repository.JpaRepository;
import cn.itcast.bos.domain.base.Standard;

/**
 * 派收标准模块数据库访问层的jpa实现接口
 * 
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月1日  下午8:30:53
 */

public interface StandardRepository extends JpaRepository<Standard, Integer> {

}

