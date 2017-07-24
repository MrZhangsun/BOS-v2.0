package cn.itcast.bos.web.action.base;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.base.TakeTime;
import cn.itcast.bos.service.base.inter.TakeTimeServiceInter;
import cn.itcast.bos.web.action.common.BaseAction;

/**
 * 时间管理模块表现层
 * 
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月9日  下午8:16:09
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
@Actions
public class TakeTimeAction extends BaseAction<TakeTime> {

        private static final long serialVersionUID = 1L;
        
        @Resource
        private TakeTimeServiceInter takeTimeServiceImpl;
        
        /**
         * 查询所有排版时间
         * 
         * @return 跳转路径
         */
        @Action(value="getTakeTimeTable", results={@Result(name="success", type="json")})
        public String getTakeTimeTable() {
                Pageable pageable = new PageRequest(page - 1, rows);
                Page<TakeTime> page = takeTimeServiceImpl.getTakeTimeTable(pageable);
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("total", page.getTotalElements());
                map.put("rows", page.getContent());
                ActionContext.getContext().getValueStack().push(map);
                return SUCCESS;
        }
        
        /**
         * 添加或者修改排班记录
         * 
         * @return 跳转路径
         */
        @Action(value="addOrUpdateTakeTime", 
                        results={@Result(name="success", 
                        location="./html/base/take_time.html", type="redirect")})
        public String addOrUpdateTakeTime() {
                takeTimeServiceImpl.save(model);
                return SUCCESS;
        }
        
        /**
         * 根据id查询对应的排班信息
         * 
         * @return 跳转路径
         */
        @Action(value="searchTakeTime", results={@Result(name="success", type="json")})
        public String searchTakeTime() {
                TakeTime takeTime = takeTimeServiceImpl.searchTakeTime(model.getId());
                ActionContext.getContext().getValueStack().push(takeTime);
                return SUCCESS;
        }
        
        private String ids;
        
        public void setIds(String ids) {
                this.ids = ids;
        }

        /**
         * 删除指定的排班记录
         * 
         * @return 跳转路径
         */
        @Action(value="deleteTakeTime", 
                        results={@Result(name="success", 
                        location="./html/base/take_time.html", type="redirect")})
        public String deleteTakeTime() {
                takeTimeServiceImpl.deleteTakeTimes(ids);
                return SUCCESS;
        }
        
        /**
         * 关联所有的取派时间
         * 
         * @return 跳转路径
         */
        @Action(value="associationTakeTime", results={@Result(name="success", type="json")})
        public String associationTakeTime() {
                List<TakeTime> takeTimes = takeTimeServiceImpl.findAll();
                ActionContext.getContext().getValueStack().push(takeTimes);
                return SUCCESS;
        }
}
