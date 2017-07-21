package cn.itcast.bos.web.action.user;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.ws.rs.core.MediaType;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.web.action.common.BaseAction;
import cn.itcast.crm.domain.Customer;
import cn.itcast.maven.bos_domain.Constant;

/**
 * 用户注册模块表示层
 * 
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月10日  下午9:37:29
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class RegistAction  extends BaseAction<Customer>{

        private static final long serialVersionUID = 1L;
        
        /** activeMQ模板 */
        @Resource(name="jmsQueueTemplate")
        private JmsTemplate jmsQueueTemplate;
        
        /** redis模板 */
        @Resource(name="redisTemplate")
        private RedisTemplate<String, String> redisTemplate;
        
        /**
         * 发送验证码
         * 
         * @return 跳转路径
         */
        @Action(value = "sendMessage")
        public String sendMessage() {
                // 生成验证码
                final String checkCode = RandomStringUtils.randomNumeric(6);
                // 将验证码存放到session中
                ServletActionContext.getRequest().getSession().setAttribute(model.getTelephone(), checkCode);
                // 将验证码和客户的电话号码存放到activeMQ中去
                final String telephone = model.getTelephone();
                jmsQueueTemplate.send("telephone", new MessageCreator() {
                        @Override
                        public Message createMessage(Session session) throws JMSException {
                                MapMessage message = session.createMapMessage();
                                message.setString("telephone", telephone);
                                message.setString("checkCode", checkCode);
                                return message;
                        }
                });
                return NONE;
        }
        
        // 接收验证码
        private String checkCode;
        
        public void setCheckCode(String checkCode) {
                this.checkCode = checkCode;
        }

        /**
         * 验证码的校验
         * 
         * @return 跳转路径
         */
        @Action(value="registCheckCode")
        public String registCheckCode() {
                try {
                        if (StringUtils.isNotBlank(checkCode)) {
                                String checdCodeSession = (String) ServletActionContext.getRequest().getSession().getAttribute(model.getTelephone());
                                if (checdCodeSession.equals(checkCode)) {
                                        ServletActionContext.getResponse().getWriter().write("1");
                                } else {
                                        ServletActionContext.getResponse().getWriter().write("0");
                                }
                        } else {
                                ServletActionContext.getResponse().getWriter().write("null");
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                }
                return NONE;
        }
        
        /**
         * 保存用户的注册信息
         * 
         * @return 跳转路径
         */
        @Action(value="customerRegist", 
                        results={@Result(name="success",location="./signup-success.html", type="redirect"),
                                        @Result(name="input", location="./signup.html", type="redirect")})
        public String customerRegist() {
                // 生成激活码
                final String activeCode = RandomStringUtils.randomAlphanumeric(32);
                // 将用户的激活码存放打redis服务器中部
                redisTemplate.opsForValue().set(model.getTelephone(), activeCode, 24, TimeUnit.HOURS);;
                // 将用户的邮箱存放到activeMQ中去,让ActiveMq完成邮件的发送
                jmsQueueTemplate.send("activeCode", new MessageCreator() {
                        @Override
                        public Message createMessage(Session session) throws JMSException {
                                MapMessage message = session.createMapMessage();
                                message.setString("telephone", model.getTelephone());
                                message.setString("email", model.getEmail());
                                message.setString("code", activeCode);
                                return message;
                        }
                });
                
                // 调用crm系统保存用户的信息
                WebClient
                        .create("http://localhost:8888/crm_management/service/userService/saveCustomer")
                        .type(MediaType.APPLICATION_JSON)
                        .post(model);
                return SUCCESS;
        }
        // 激活邮件发送的验证码
        private String activeCode;
        
        public void setActiveCode(String activeCode) {
                this.activeCode = activeCode;
        }

        /**
         * 邮箱激活
         * 
         * @return 跳转路径
         */
        @Action(value="activeCustomer")
        public String activeCustomer() {
                // 判断激活码是否有效
                String redis_code = redisTemplate.opsForValue().get(model.getTelephone());
                if (redis_code != null && activeCode.equals(redis_code)) {
                        // 查询邮箱是否已经绑定                                                                                         
                        Customer customer = WebClient.create("http://localhost:8888/crm_management/service/userService/findByTelephone/"+model.getTelephone())
                        .accept(MediaType.APPLICATION_JSON)
                        .get(Customer.class);
                        if (customer != null && customer.getType() == null) {
                                // 进行邮箱绑定
                                WebClient.create("http://localhost:8888/crm_management/service/userService/activeCustomer")
                                .put(model.getTelephone());
                        } else {
                                // 重复绑定
                                try {
                                        ServletActionContext.getResponse().getWriter().println("您的邮箱已经绑定到当前账户,请不要重复绑定!");
                                } catch (IOException e) {
                                        e.printStackTrace();
                                }
                        }
                } else {
                        try {
                                ServletActionContext.getResponse().getWriter().println("您的激活码已经过期,请登录系统,重新绑定邮箱.");
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                }
                // 调用crm系统修改用户的激活状态
                return NONE;
        }
        
        /**
         * 用户名是否存在的查询
         * 
         * @return  跳转视图
         */
        @Action(value="isExist", results={@Result(name="success", type="json")})
        public String isExist() {
                String telephone = model.getTelephone();
                if (telephone != null || telephone != "") {
                        HashMap<String, Integer> map = new HashMap<String, Integer>();
                        Customer customer = WebClient.create(Constant.CRM_MANAGEMENT_URL+"/findByTelephone/"+telephone)
                        .accept(MediaType.APPLICATION_JSON).get(Customer.class);
                        if (customer != null) {
                               map.put("result", 1);
                        } else {
                                map.put("result", 0);
                        }
                        ServletActionContext.getContext().getValueStack().push(map);
                }
                return SUCCESS;
        }
        
}
