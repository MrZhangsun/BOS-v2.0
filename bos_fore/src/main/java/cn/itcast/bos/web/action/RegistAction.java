package cn.itcast.bos.web.action;

import java.io.IOException;
import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import cn.itcast.bos.web.action.base.BaseAction;
import cn.itcast.crm.domain.Customer;
import redis.clients.jedis.Jedis;

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
        
        @Resource(name="jmsQueueTemplate")
        private JmsTemplate jmsQueueTemplate;
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
                String activeCode = RandomStringUtils.randomAlphanumeric(32);
                // 创建redis服务器连接
                Jedis client = new Jedis("localhost", 6379);
                // 将激活码存放到redis服务器,设置有效期
                client.set("activeCode", activeCode, "30");
                // 将用户的邮箱存放到activeMQ中去,让ActiveMq完成邮件的发送
                
                // 调用crm系统保存用户的信息
                Response response = WebClient
                        .create("http://localhost:8888/crm_management/service/userService/saveCustomer")
                        .type(MediaType.APPLICATION_JSON)
                        .post(model);
                System.out.println(response);
                
                return SUCCESS;
        }
}
