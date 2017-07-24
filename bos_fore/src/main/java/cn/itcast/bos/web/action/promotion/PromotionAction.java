package cn.itcast.bos.web.action.promotion;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import javax.ws.rs.core.MediaType;
import org.apache.commons.io.FileUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import cn.itcast.bos.web.action.common.BaseAction;
import cn.itcast.maven.bos_domain.Constant;
import cn.itcast.maven.bos_domain.PageBean;
import cn.itcast.maven.bos_domain.Promotion;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 促销模块的表示层
 *
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月17日  下午1:49:34
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class PromotionAction extends BaseAction<Promotion> {

        private static final long serialVersionUID = 1L;

        /**
         * 查询主页的促销数据
         * 
         * @return 跳转路径
         */
        @Action(value="findMainPromotions", results={@Result(name="success", type="json")})
        public String findMainPromotions() {
                @SuppressWarnings("unchecked")
                PageBean<Promotion> pageBean = WebClient.create(Constant.MANAGEMENT_URL
                                + "/promotionService/findPromotionByPage?page="+page+"&rows="+rows)
                .accept(MediaType.APPLICATION_JSON).get(PageBean.class);

                ServletActionContext.getContext().getValueStack().push(pageBean);
                return SUCCESS;
        }
        
        /**
         * 根据id加载促销详情
         * 
         * @return 促销对象
         */
        @Action(value="promotion_showDetail")
        public String promotion_showDetail() {
                String realPath = ServletActionContext.getServletContext().getRealPath("/WEB-INF/template");
                File templateFile = new File(realPath + "/" + model.getId() + ".html");
                System.out.println(templateFile.getAbsolutePath());
                if (!templateFile.exists()) {
                        Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);
                        try {
                                configuration.setDirectoryForTemplateLoading(new File(realPath));
                                Template template = configuration.getTemplate("promotion_detail.ftl");
                                // 动态获取促销数据
                                Promotion promotion = WebClient.create(Constant.MANAGEMENT_URL + "/promotionService/findPromotionById/"+model.getId())
                                                .accept(MediaType.APPLICATION_JSON).get(Promotion.class);
                                HashMap<String, Object> map = new HashMap<String, Object>();
                                map.put("promotion", promotion);
                                
                                template.process(map, new OutputStreamWriter(new FileOutputStream(templateFile), "utf-8"));
                                
                                ServletActionContext.getResponse().setContentType("text/html; charset=utf-8");
                                FileUtils.copyFile(templateFile, ServletActionContext.getResponse().getOutputStream());
                        } catch (IOException | TemplateException e) {
                                e.printStackTrace();
                        }
                }
                return NONE;
        }
        
}
