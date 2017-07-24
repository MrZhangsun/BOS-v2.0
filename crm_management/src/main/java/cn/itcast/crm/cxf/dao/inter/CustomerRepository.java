package cn.itcast.crm.cxf.dao.inter;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.crm.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

        /**
         * 查询所有没有关联定区的客户
         * 
         * @return 客户的集合
         */
        List<Customer> findByFixedAreaIdIsNull();

        /**
         * 查询所有已经关联定区的客户
         * 
         * @return 所有已经关联定区的客户的集合
         */
        List<Customer> findByFixedAreaId(String id);

        /**
         * 将用户关联到指定的定区上
         * 
         * @param CustomerIds 指定要关联的客户id的字符串
         * @param fixId 定区id
         */
        @Query("update Customer set fixedAreaId = ?2 where id = ?1")
        @Modifying
        void associatCustomerToFixedArea(Integer id, String fixId);

        /**
         * 激活用户
         * 
         * @param telephone 用户的手机号码(用户名)
         */
        @Query("update Customer set type = 1 where telephone = ?")
        @Modifying
        void activeCustomer(String telephone);

        Customer findByTelephone(String telephone);

        /**
         * 用户登录
         * 
         * @param telephone 用户名(手机号)
         * @param password 密码
         * @return 用户对象
         */
        Customer findByTelephoneAndPassword(String telephone, String password);

        /**
         * 根据客户下单地址查询对应的定区id
         * 
         * @return 定区的id
         */
        @Query(value="select fixedAreaId from Customer where address = ?")
        String findFixedareaIdByAddress();

}
