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
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.inter.StandardServiceInter;

/**
 * 基础数据模块表示层
 * 
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月1日  下午6:04:10
 */

@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
@Actions
public class StandardAction extends ActionSupport implements ModelDriven<Standard> {

        private static final long serialVersionUID = 1L;
        
        /** 派收标准 */
        private Standard standard = new Standard();
        
        /** 封装派收标准 */
        @Override
        public Standard getModel() {
                return standard;
        }

        /** 注入业务层的标准接口实现类 */
        @Resource
        private StandardServiceInter standardServiceImpl;
        
        /**
         * 保存标准
         * 
         * @return 转发路径
         */
        @Action(value="standard_save", results={@Result(name="success", location="/html/base/standard.html", type="redirect")})
        public String addStandard() {
                standardServiceImpl.standardSave(standard);
                return SUCCESS;
        }

        /**
         * 查询标准
         * 
         * @return 转发路径
         */
        @Action(value="standard_pageQuery", results={@Result(name="success",  type="json")})
        public String queryStandard() {
                // 调用业务层查询所有的分页数据
                Pageable pageable = new PageRequest(page - 1, rows);
                Page<Standard> pages = standardServiceImpl.pageQuery(pageable);
                long count = pages.getTotalElements();
                List<Standard> content = pages.getContent();
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                hashMap.put("total", count);
                hashMap.put("rows", content);
                // 将数据压到栈顶，springdata插件会从栈顶将数据转换成json格式
                ActionContext.getContext().getValueStack().push(hashMap);
                return SUCCESS;
        }

        /**
         * 修改标准
         * 
         * @return 跳转路径
         */
        @Action(value = "rowUpdate", results={@Result(name="success", type="json")})
        public String rowUpdate() {
                Standard _standard = standardServiceImpl.rowUpdate(standard.getId());
                ActionContext.getContext().getValueStack().push(_standard);
                return SUCCESS;
        }
        
        /**
         * 查询所有的取派标准
         * 
         * @return 跳转路径
         */
        @Action(value="findStandard", results={@Result(name="success",type="json")})
        public String findStandards() {
                List<Standard> standards = standardServiceImpl.findStandards();
                ActionContext.getContext().getValueStack().push(standards);
                return SUCCESS;
        }
        // 接收分页查询的当前页和分页单位
        private int page;
        private int rows;
        
        public void setPage(int page) {
                this.page = page;
        }

        public void setRows(int rows) {
                this.rows = rows;
        }

}
