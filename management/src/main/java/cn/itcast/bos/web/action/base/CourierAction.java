package cn.itcast.bos.web.action.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.inter.CourierServiceInter;

/**
 * 员工模块表现层
 * 
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月3日  上午8:49:13
 */
@Controller
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/")
@Actions
public class CourierAction extends ActionSupport implements ModelDriven<Courier> {

        private static final long serialVersionUID = 1L;
        
        @Resource
        private CourierServiceInter courierServiceInter;
        
        /**  取派员 */
        private Courier courier = new Courier();
        
        /** 分页单位 */
        private int rows;
        
        /** 当前页 */
        private int page;
        
        /** 作废或恢复标志 */
        private String method;
        
        /** 作废的取派员的id */
        private String ids;
        
        @Override
        public Courier getModel() {
                return courier;
        }

        /**
         * 分页查询取派员
         * 
         * @return 跳转路径
         */
        @Action(value="pageQueryCouriers", results={@Result(name="success", type="json")})
        public String pageQueryCouriers() {
                
                // 创建一个条件
                Specification<Courier> specification = new Specification<Courier>() {
                        ArrayList<Predicate> list = new ArrayList<Predicate>();
                        @Override
                        public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                                // 按照员工的工号查询进行精确查询
                                if (StringUtils.isNotBlank(courier.getCourierNum())) {
                                        Predicate p1 = cb.equal(root.get("courierNum").as(String.class), courier.getCourierNum());
                                        list.add(p1);
                                }
                                // 按照员单位进行模糊查询
                                if (StringUtils.isNotBlank(courier.getCompany())) {
                                        // select * from t_user where username like %DD%;
                                       Predicate p2 = cb.like(root.get("company").as(String.class), "%"+courier.getCompany()+"%");
                                       list.add(p2);
                                }
                                // 按照派件员的类型进行模糊查询
                                if (StringUtils.isNotBlank(courier.getType())) {
                                        Predicate p3 = cb.equal(root.get("type"), courier.getType());
                                        list.add(p3);
                                }
                                // 按照派件标准进行多表连接精确查询
                                Join<Object, Object> join = root.join("standard", JoinType.INNER);
                                if (courier.getStandard() != null && StringUtils.isNotBlank(courier.getStandard().getName())) {
                                        Predicate p4 = cb.like(join.get("name").as(String.class), "%"+ courier.getStandard().getName()+"%" );
                                        list.add(p4);
                                }
                                return cb.and(list.toArray(new Predicate[0]));
                        }
                };
        Pageable pageable = new PageRequest(page-1, rows);
        Page<Courier> couriers = courierServiceInter.findAll(specification,pageable);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("total", couriers.getTotalElements());
        map.put("rows", couriers.getContent());
        ActionContext.getContext().getValueStack().push(map);
        return SUCCESS;
        }
        /**
         * 保存或修改取派员信息
         * 
         * @return 跳转路径
         */
        @Action(value="saveCourier", results={@Result(name="success", 
                        location="./html/base/courier.html", type="redirect"),
                        @Result(name="input", location="/error.jsp")})
        public String saveCourier() {
                courierServiceInter.saveCourier(courier);
                return SUCCESS;
        }
        
        /** 查询指定的派件员
         * 
         * @return 跳转路径
         */
        @Action(value="findOneCourier", results={@Result(name="success", type="json")})
        public String findOneCourier() {
                Courier _courier = courierServiceInter.findOneCourier(courier.getId());
                ActionContext.getContext().getValueStack().push(_courier);
                return SUCCESS;
        }
        
        /**
         * 作废取派员信息
         * 
         * @return 跳转路径
         */
        @Action(value="delBatch", results={@Result(name="success",
                        location="./html/base/courier.html", type="redirect")})
        public String delBatch() {
                String[] idStrs = ids.split("-");
                courierServiceInter.delOrRecoverBatch(idStrs, method);
                return SUCCESS;
        }
        
        
        
        /**
         * 查询所有的快递员
         * 
         * @return 跳转路径
         */
        @Action(value="associationCourier", results={@Result(name="success", type="json")})
        public String associationCourier() {
                List<Courier> couriers = courierServiceInter.findAssociationCourier();
                ActionContext.getContext().getValueStack().push(couriers);
                return SUCCESS;
        }
        
        public void setRows(int rows) {
                this.rows = rows;
        }

        public void setPage(int page) {
                this.page = page;
        }

        public void setIds(String ids) {
                this.ids = ids;
        }

        public void setMethod(String method) {
                this.method = method;
        }
}
