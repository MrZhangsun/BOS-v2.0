package cn.itcast.bos.web.action.user;

import java.io.IOException;
import java.util.HashMap;
import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.ws.rs.core.MediaType;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import cn.itcast.bos.web.action.common.BaseAction;
import cn.itcast.bos.web.util.CheckImgUtil;
import cn.itcast.crm.domain.Customer;
import cn.itcast.maven.bos_domain.Constant;

/**
 * 用户登录
 *
 * @author 长孙建坤 18092853734
 * @version 1.0 ，2017年7月18日 下午1:57:11
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
@Actions
public class Login extends BaseAction<Customer> {

        private static final long serialVersionUID = 1L;

        @Resource
        private JmsTemplate jmsQueueTemplate;

        private String recordUserName;

        public void setRecordUserName(String recordUserName) {
                this.recordUserName = recordUserName;
        }

        private String checkCode;

        public void setCheckCode(String checkCode) {
                this.checkCode = checkCode;
        }

        /**
         * 登录方式的判断标志
         */
        private String article;

        public void setArticle(String article) {
                this.article = article;
        }

        /**
         * 用户名密码登录
         * 
         * @return
         */
        @Action(value = "login", results = {
                        @Result(name = "success", location = "index.html#/myhome", type = "redirect"),
                        @Result(name = "login", location = "login.html", type = "redirect") })
        public String login() {
                Customer customer;
                if (article != null && "0".equals(article)) {
                        customer = WebClient
                                        .create(Constant.CRM_MANAGEMENT_URL + "/userPasswordLogin?telephone="
                                                        + model.getUsername() + "&password=" + model.getPassword())
                                        .accept(MediaType.APPLICATION_JSON).get(Customer.class);
                        if (customer != null) {
                                // 记住用户名
                                if ("on".equals(recordUserName)) {
                                        ServletActionContext.getRequest().getSession().setAttribute("recordUserName",
                                                        customer.getTelephone());
                                }
                                // 将用户的验证码存到session中去
                                ServletActionContext.getRequest().getSession().setAttribute("checkCode", checkCode);
                                // 将登录成功的信息存放到session中
                                ServletActionContext.getRequest().getSession().setAttribute("loginedUser", customer);
                                return SUCCESS;
                        } else {
                                // 登录失败将用户的信息存放到cookie中
                                Cookie cookie = new Cookie("loginFail", "0");
                                cookie.setPath(ServletActionContext.getRequest().getContextPath());
                                ServletActionContext.getResponse().addCookie(cookie);
                                return LOGIN;
                        }
                } else {
                         customer = WebClient
                                        .create(Constant.CRM_MANAGEMENT_URL + "/findByTelephone/"+model.getTelephone())
                                        .accept(MediaType.APPLICATION_JSON).get(Customer.class);
                         if (customer != null) {
                                 // 将用户的验证码存到session中去
                                 ServletActionContext.getRequest().getSession().setAttribute("checkCode", checkCode);
                                 // 将登录成功的信息存放到session中
                                 ServletActionContext.getRequest().getSession().setAttribute("loginedUser", customer);
                                 return SUCCESS;
                         } else {
                                 // 登录失败将用户的信息存放到cookie中
                                 Cookie cookie = new Cookie("loginFail", "2");
                                 cookie.setPath(ServletActionContext.getRequest().getContextPath());
                                 ServletActionContext.getResponse().addCookie(cookie);
                                 return LOGIN;
                         }
                }
        }

        /**
         * 用户登录信息的异步加载
         * 
         * @return 跳转路径
         */
        @Action(value = "getSessionInfo", results = { @Result(name = "success", type = "json") })
        public String getSessionInfo() {
                Customer loginedUser = (Customer) ServletActionContext.getRequest().getSession()
                                .getAttribute("loginedUser");
                if (loginedUser != null) {
                        ServletActionContext.getContext().getValueStack().push(loginedUser);
                }
                return SUCCESS;
        }

        /**
         * 记住用户名的异步加载
         * 
         * @return 跳转路径
         */
        @Action(value = "getRecordeUserName", results = { @Result(name = "success", type = "json") })
        public String getRecordeUserName() {
                String username = (String) ServletActionContext.getRequest().getSession()
                                .getAttribute("recordUserName");
                if (username != null) {
                        ServletActionContext.getContext().getValueStack().push(username);
                        return SUCCESS;
                } else {
                        return NONE;
                }
        }

        /**
         * 生成验证码
         * 
         * @return
         */
        @Action(value = "getCheckImg")
        public String getCheckImg() {
                try {
                        CheckImgUtil.getImgeCode(ServletActionContext.getRequest(), ServletActionContext.getResponse());
                } catch (ServletException e) {
                        e.printStackTrace();
                } catch (IOException e) {
                        e.printStackTrace();
                }
                return NONE;
        }

        private String imgCode;

        public void setImgCode(String imgCode) {
                this.imgCode = imgCode;
        }

        /**
         * 验证码的校验
         * 
         * @return 跳转路径
         */
        @Action(value = "checkCodeImg", results = { @Result(name = "success", type = "json") })
        public String checkCodeImg() {
                StringBuilder coder = (StringBuilder) ServletActionContext.getRequest().getSession()
                                .getAttribute("coder");

                HashMap<String, Integer> map = new HashMap<String, Integer>();
                if (coder != null && coder.toString().equals(imgCode)) {
                        map.put("result", 1);
                        ServletActionContext.getContext().getValueStack().push(map);
                } else {
                        map.put("result", 0);
                        ServletActionContext.getContext().getValueStack().push(map);
                }
                return SUCCESS;
        }

        /**
         * 发送短信验证码
         * 
         * @return 跳转路径
         */
        @Action(value = "sendCheckCode")
        public void sendCheckCode() {
                final String randomCode = RandomStringUtils.randomNumeric(6);
                ServletActionContext.getRequest().getSession().setAttribute("randomCode", randomCode);
                jmsQueueTemplate.send("loginRandomCode", new MessageCreator() {

                        @Override
                        public Message createMessage(Session session) throws JMSException {
                                MapMessage mapMessage = session.createMapMessage();
                                mapMessage.setString("telephone", model.getTelephone());
                                mapMessage.setString("randomCode", randomCode);
                                return mapMessage;
                        }
                });
        }

        /**
         * 手机短信验证码的校验
         * 
         * @return 跳转视图
         */
        @Action(value = "submitCheckCode", results = { @Result(name = "success", type = "json") })
        public String submitCheckCode() {
                String randomCode = (String) ServletActionContext.getRequest().getSession().getAttribute("randomCode");
                HashMap<String, Integer> map = new HashMap<String, Integer>();
                if (randomCode != null && randomCode.toString().equals(checkCode)) {
                        map.put("result", 1);
                        ServletActionContext.getContext().getValueStack().push(map);
                } else {
                        map.put("result", 0);
                        ServletActionContext.getContext().getValueStack().push(map);
                }
                return SUCCESS;
        }

}
