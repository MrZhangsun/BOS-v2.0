package cn.itcast.bos.dao.base.inter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.base.Courier;

/**
 * 取派员持久层接口
 * 
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月3日  下午3:27:12
 */
public interface CourierRepository extends JpaSpecificationExecutor<Courier>, JpaRepository<Courier, Integer> {

        @Query("update Courier set deltag ='1' where id = ?")
        @Modifying
        void delBatch(Integer id);

        @Query("update Courier set deltag =null where id = ?")
        @Modifying
        void recoverBatch(int parseInt);
        

}
