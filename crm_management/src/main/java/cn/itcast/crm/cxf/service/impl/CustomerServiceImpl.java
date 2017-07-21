package cn.itcast.crm.cxf.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.crm.cxf.dao.inter.CustomerRepository;
import cn.itcast.crm.cxf.service.inter.CustomerServiceInter;
import cn.itcast.crm.domain.Customer;

/**
 * 服务器端资源接口实现类
 * 
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月7日  下午8:26:40
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerServiceInter {

        @Resource
        private CustomerRepository customerRepository;
        
        /**
         * @see cn.itcast.crm.cxf.service.inter.CustomerServiceInter#findNoAssociationCustomers()
         */
        @Override
        public List<Customer> findNoAssociationCustomers() {
                return customerRepository.findByFixedAreaIdIsNull();
        }

        /**
         * @see cn.itcast.crm.cxf.service.inter.CustomerServiceInter#findAssociationCustomers(String)
         */
        @Override
        public List<Customer> findAssociationCustomers(String id) {
                return customerRepository.findByFixedAreaId(id);
        }

        /**
         * @see cn.itcast.crm.cxf.service.inter.CustomerServiceInter#associateToFixedArea(String, String)
         */
        @Override
        public void associateToFixedArea(String customerIds, String fixId) {
                if (StringUtils.isNotBlank(customerIds)) {
                        String[] custmerIdsArray = customerIds.split("-");
                        for (String id : custmerIdsArray) {
                                customerRepository.associatCustomerToFixedArea(Integer.parseInt(id), fixId);
                        }
                }
        }

        /**
         * @see cn.itcast.crm.cxf.service.inter.CustomerServiceInter#saveCustomer(Customer)
         */
        @Override
        public void saveCustomer(Customer customer) {
                customerRepository.save(customer);
        }

        /**
         * @see cn.itcast.crm.cxf.service.inter.CustomerServiceInter#activeCustomer(String)
         */
        @Override
        public void activeCustomer(String telephone) {
                customerRepository.activeCustomer(telephone);
        }

        /**
         * @see cn.itcast.crm.cxf.service.inter.CustomerServiceInter#findByTelephone(String)
         */
        @Override
        public Customer findByTelephone(String telephone) {
                return customerRepository.findByTelephone(telephone);
        }

        /**
         * @see cn.itcast.crm.cxf.service.inter.CustomerServiceInter#userLogin(String, String)
         */
        @Override
        public Customer userLogin(String telephone, String password) {
                return customerRepository.findByTelephoneAndPassword(telephone, password);
        }

}
