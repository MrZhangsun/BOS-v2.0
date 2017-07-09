package cn.itcast.crm.cxf.service.inter;

import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import cn.itcast.crm.domain.Customer;

/**
 * 服务器资源类的测试类
 * 
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月8日  上午8:10:10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class CustomerServiceInterTest {

        @Resource
        private CustomerServiceInter customerServiceInter;
                
        @Test
        public void testFindNoAssociationCustomers() {
                List<Customer> customers = customerServiceInter.findNoAssociationCustomers();
                System.out.println(customers);
        }

        @Test
        public void testFindAssociationCustomers() {
                List<Customer> customers = customerServiceInter.findAssociationCustomers("dq1002");
                System.out.println(customers);
        }

        @Test
        public void testAssociateToFixedArea() {
                customerServiceInter.associateToFixedArea("1", "dq1003");
        }

}
