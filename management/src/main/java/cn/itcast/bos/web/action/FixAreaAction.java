package cn.itcast.bos.web.action;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.service.inter.FixAreaServiceInter;
import cn.itcast.bos.web.action.base.BaseAction;

/**
 * 定区模块表示层
 * 
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月5日  下午7:51:27
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
@Actions
public class FixAreaAction extends BaseAction<FixedArea> {

        private static final long serialVersionUID = 1L;
        
        @Resource
        private FixAreaServiceInter fixAreaServiceInter;
        
        /**
         * 添加或者修改定区信息
         * 
         * @return 跳转路经
         */
        @Action(value="addOrUpdateFixArea",
                        results={@Result(name="success", location="./html/base/fixed_area.html", type="redirect")})
        public String addOrUpdateFixArea() {
                fixAreaServiceInter.addOrUpdateFixArea(model);
                return SUCCESS;
        }
        
        /**
         * 查询修改的定区信息进行回显
         * 
         * @return 跳转路径
         */
        @Action(value="updateFixedArea", results={@Result(name="success", type="json")})
        public String updateFixedArea() {
                FixedArea fixedArea = fixAreaServiceInter.updateFixedArea(model.getId());
                ActionContext.getContext().getValueStack().push(fixedArea);
                return SUCCESS;
        }
        
        /**
         * 分页查询所有的
         * 
         * @return 跳转路径
         */
        @Action(value="fixedAreaQuery", 
                        results={@Result(name="success", type="json")})
        public String fixedAreaQuery() {
                // 封装分页数据
                Pageable pageable = new PageRequest(page - 1, rows);
                Specification<FixedArea> specification = new Specification<FixedArea>() {
                        @Override
                        public Predicate toPredicate(Root<FixedArea> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                                ArrayList<Predicate> list = new ArrayList<Predicate>();
                                // 构建条件
                                if (StringUtils.isNotBlank(model.getId())) {
                                        Predicate p1 = cb.equal(root.get("id").as(String.class), model.getId());
                                        list.add(p1);
                                }
                                if (StringUtils.isNotBlank(model.getCompany())) {
                                        Predicate p2 = cb.like(root.get("company").as(String.class), "%"+model.getCompany()+"%");
                                        list.add(p2);
                                }
                               /* if (StringUtils.isNotBlank(model.getSubareas())) {
                                        Predicate p3 = cb.like(root.get("subareaName").as(String.class), "%"+model.getSubareas()+"%");
                                        list.add(p3);
                                }
                                
                                */
                                return cb.and(list.toArray(new Predicate[0]));
                        }
                };
                Page<FixedArea> fixedAreas = fixAreaServiceInter.fixedAreaQuery(specification, pageable);
                pushToStack(fixedAreas);
                return SUCCESS;
        }
        
        /**
         * 删除指定的定区
         * 
         * @return 跳转路径
         */
        @Action(value="deleteFixedArea", 
                        results={@Result(name="success", location="./html/base/fixed_area.html", type="redirect")})
        public String deleteFixedArea() {
                String id = model.getId();
                String[] ids = id.split("-");
                fixAreaServiceInter.deleteFixedArea(ids);
                return SUCCESS;
        }
   
}
