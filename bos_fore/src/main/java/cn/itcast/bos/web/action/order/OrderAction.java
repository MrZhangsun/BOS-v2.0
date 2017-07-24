package cn.itcast.bos.web.action.order;

import javax.ws.rs.core.MediaType;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.order.Order;
import cn.itcast.bos.web.action.common.BaseAction;
import cn.itcast.crm.domain.Customer;
import cn.itcast.maven.bos_domain.Constant;

/**
 * 订单模块表示层
 *
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月24日  上午7:44:43
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class OrderAction extends BaseAction<Order> {

        private static final long serialVersionUID = 1L;
        
        private String recAreaInfo;
        
        private String sendAreaInfo;
        
        public void setRecAreaInfo(String recAreaInfo) {
                this.recAreaInfo = recAreaInfo;
        }

        public void setSendAreaInfo(String sendAreaInfo) {
                this.sendAreaInfo = sendAreaInfo;
        }

        /**
         * 生成订单
         * 
         * @return 跳转视图
         */
        @Action(value="saveOrder", results={@Result(name="success", location="./index.html", type="redirect")})
        public String saveOrder() {
                Area sendArea = new Area();
                String[] sendAreaAddress = sendAreaInfo.split("/");
                sendArea.setProvince(sendAreaAddress[0]);
                sendArea.setCity(sendAreaAddress[1]);
                sendArea.setDistrict(sendAreaAddress[2]);
                
                Area recArea = new Area();
                String[] recAreaAddress = recAreaInfo.split("/");
                recArea.setProvince(recAreaAddress[0]);
                recArea.setCity(recAreaAddress[1]);
                recArea.setDistrict(recAreaAddress[2]);
                
                model.setSendArea(sendArea);
                model.setRecArea(recArea);
                
                Customer customer = (Customer) ServletActionContext.getRequest().getSession().getAttribute("customer");
                model.setCustomer_id(customer.getId());
                
                WebClient.create(Constant.MANAGEMENT_URL+"/orderService/saveOrder")
                                .type(MediaType.APPLICATION_JSON)
                                .post(model);
                
                return SUCCESS;
        }
        
}
