package cn.itcast.bos.web.action;

import java.io.File;
import java.util.List;
import javax.annotation.Resource;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.opensymphony.xwork2.ActionContext;
import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.service.inter.SubAreaServiceInter;
import cn.itcast.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/")
@Actions
public class SubAreaAction extends BaseAction<SubArea>{

        private static final long serialVersionUID = 1L;
        
        @Resource
        private SubAreaServiceInter subAreaServiceImpl;
        
        // 文件上传
        private File file;
        
        public void setFile(File file) {
                this.file = file;
        }

        /**
         * 导入分区
         * 
         * @return 跳转路径
         */
        
        private String importSubArea() {
                
                return SUCCESS;
        }
        
        /**
         * 分页查询所有的分区数据
         * 
         * @return 跳转路径
         */
        @Action(value="findSubArea", results={@Result(name="success", type="json")})
        public String findSubArea() {
                List<SubArea> subAreas = subAreaServiceImpl.findSubArea();     
                ActionContext.getContext().getValueStack().push(subAreas);
                return SUCCESS;
        }
        
        public String ids;
        
        
        public void setIds(String ids) {
                this.ids = ids;
        }

        /**
         * 删除指定的区域
         * 
         * @return 跳转路径
         */
         @Action(value="deleteArea", 
                         results={@Result(name="success", location="./html/base/area.html", type="redirect")})
        public String deleteArea() {
                String[] del = ids.split("-");
                subAreaServiceImpl.deleteArea(del);
                return SUCCESS;
        }

}
