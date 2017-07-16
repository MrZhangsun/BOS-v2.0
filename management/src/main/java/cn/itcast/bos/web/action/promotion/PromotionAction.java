package cn.itcast.bos.web.action.promotion;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.service.promotion.inter.PromotionServiceInter;
import cn.itcast.bos.web.action.base.BaseAction;
import cn.itcast.maven.bos_domain.Promotion;

/**
 * 商品促销模块表示层
 *
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月15日  下午8:42:39
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class PromotionAction extends BaseAction<Promotion>{
        private static final long serialVersionUID = 1L;
        
        @Resource
        private PromotionServiceInter promotionServiceInter;

        private File imgFile;
        
        private String imgFileFileName;

        public void setImgFileFileName(String imgFileFileName) {
                this.imgFileFileName = imgFileFileName;
        }

        public void setImgFile(File imgFile) {
                this.imgFile = imgFile;
        }

        /**
         * 文件上传
         * 
         * @return
         */
        @Action(value="fileUpload", results={@Result(name="success", type="json")})
        public String fileUpload() {
                // 获取上传文件的存放位置
                String uploadPath = ServletActionContext.getServletContext().getRealPath("/upload/image/");
                // 生成随机文件名
                String uuid = UUID.randomUUID().toString();
                // 获取文件的扩展名
                String ext = imgFileFileName.substring(imgFileFileName.lastIndexOf("."));
                File destFile = new File(uploadPath, uuid+ext);
                try {
                        FileUtils.copyFile(imgFile, destFile);
                } catch (IOException e) {
                        e.printStackTrace();
                }
                
                // 文件上传成功了,将url返回给浏览器
                String contextPath = ServletActionContext.getRequest().getContextPath();
                String url = contextPath + "/upload/image/" + uuid + ext;
                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                hashMap.put("error", 0);
                hashMap.put("url", url);
                ServletActionContext.getContext().getValueStack().push(hashMap);
                return SUCCESS;
        }
        
        /**
         * 服务器端图片的浏览管理
         * 
         * @return 跳转路径
         * @throws IOException 
         */
        @Action(value="fileManagement", results={@Result(name="success", type="json")})
        public String fileManagement() throws IOException {
              // 设置响应的编码
                ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
              // 根目录路径，可以指定绝对路径，比如 /var/www/attached/
                String rootPath = ServletActionContext.getServletContext().getRealPath("/") + "upload/";
                // 根目录URL，可以指定绝对路径，比如 http://www.yoursite.com/attached/
                String rootUrl  = ServletActionContext.getRequest().getContextPath() + "/upload/";
                // 图片扩展名
                String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};
                
                // 从请求参数中获取文件夹的名称
                String dirName = ServletActionContext.getRequest().getParameter("dir");
                // 如果请求的文件夹名不等于空
                if (dirName != null) {
                        // 判断请求的文件夹名称是否有效
                        if(!Arrays.<String>asList(new String[]{"image", "flash", "media", "file"}).contains(dirName)){
                                ServletActionContext.getResponse().getWriter().println("Invalid Directory name.");
                                return NONE;
                        }
                        rootPath += dirName + "/";
                        rootUrl += dirName + "/";
                        File saveDirFile = new File(rootPath);
                        if (!saveDirFile.exists()) {
                                saveDirFile.mkdirs();
                        }
                        
                      //根据path参数，设置各路径和URL
                        String path = ServletActionContext.getRequest().getParameter("path") != null ? ServletActionContext.getRequest().getParameter("path") : "";
                        String currentPath = rootPath + path;
                        String currentUrl = rootUrl + path;
                        String currentDirPath = path;
                        String moveupDirPath = "";
                        // 如果path不等于""
                        if (!"".equals(path)) {
                                String str = currentDirPath.substring(0, currentDirPath.length() - 1);
                                moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
                        }
                        
                      //排序形式，name or size or type(默认按照名称)
                        String order = ServletActionContext.getRequest().getParameter("order") != null ? ServletActionContext.getRequest().getParameter("order").toLowerCase() : "name";
                        
                      //最后一个字符不是/
                        if (!"".equals(path) && !path.endsWith("/")) {
                                ServletActionContext.getResponse().getWriter().println("Parameter is not valid.");
                                return NONE;
                        }
                        //目录不存在或不是目录
                        File currentPathFile = new File(currentPath);
                        if(!currentPathFile.isDirectory()){
                                ServletActionContext.getResponse().getWriter().println("Directory does not exist.");
                                return NONE;
                        }
                        
                        // 遍历文件夹中的所有文件
                        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
                        File[] listFiles = currentPathFile.listFiles();
                        if (listFiles != null) {
                                for (File file : listFiles) {
                                        HashMap<String, Object> map = new HashMap<String, Object>();
                                        String fileName = file.getName();
                                        if (file.isDirectory()) {
                                                map.put("is_dir", true);
                                                map.put("has_file", (file.listFiles() != null));
                                                map.put("filesize", 0L);
                                                map.put("is_photo", false);
                                                map.put("filetype", "");
                                        } else {
                                                String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                                                map.put("is_dir", false);
                                                map.put("has_file", false);
                                                map.put("filesize", file.length());
                                                map.put("is_photo", Arrays.<String>asList(fileTypes).contains(ext));
                                                map.put("filetype", ext);
                                        }
                                        map.put("filename", fileName);
                                        map.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
                                        list.add(map);
                                }  
                        }
                        // 文件排序功能的实现
                        if ("size".equals(order)) {
                                Collections.sort(list, new Comparator<HashMap<String, Object>>() {

                                        @Override
                                        public int compare(HashMap<String, Object> o1, HashMap<String, Object> o2) {
                                                // 如果是文件夹就排在后面
                                                if ((boolean) o1.get("is_dir") && !(boolean)o2.get("is_dir")) {
                                                        return -1;
                                                } else if (!(boolean)o1.get("is_dir") && (boolean)o2.get("is_dir")) {
                                                        return 1;
                                                } else {
                                                        long result = (long) o1.get("filesize") - (long) o2.get("filesize");
                                                        if (result > 0) {
                                                                return 1;
                                                        } else if (result < 0) {
                                                                return -1;
                                                        } else {
                                                                return 0;
                                                        }
                                                }
                                        }

                                });
                        } else if ("type".equals(order)) {
                                Collections.sort(list, new Comparator<HashMap<String, Object>>() {

                                        @Override
                                        public int compare(HashMap<String, Object> o1, HashMap<String, Object> o2) {
                                                // 如果是文件夹就排在后面
                                                if ((boolean) o1.get("is_dir") && !(boolean)o2.get("is_dir")) {
                                                        return -1;
                                                } else if (!(boolean)o1.get("is_dir") && (boolean)o2.get("is_dir")) {
                                                        return 1;
                                                } else {
                                                        return ((String)o1.get("filetype")).compareTo((String)o2.get("filetype"));
                                                }
                                        }
                                });
                        } else {
                                Collections.sort(list, new Comparator<HashMap<String, Object>>() {

                                        @Override
                                        public int compare(HashMap<String, Object> o1, HashMap<String, Object> o2) {
                                                // 如果是文件夹就排在后面
                                                if ((boolean) o1.get("is_dir") && !(boolean)o2.get("is_dir")) {
                                                        return -1;
                                                } else if (!(boolean)o1.get("is_dir") && (boolean)o2.get("is_dir")) {
                                                        return 1;
                                                } else {
                                                        return ((String)o1.get("filename")).compareTo((String)o2.get("filename"));
                                                }
                                        }
                                });
                        }
                        
                        // 将参数压入到值栈转换成json
                        HashMap<String, Object> values = new HashMap<String, Object>();
                        values.put("moveup_dir_path", moveupDirPath);
                        values.put("current_dir_path", currentDirPath);
                        values.put("current_url", currentUrl);
                        values.put("total_count", list.size());
                        values.put("file_list", list);
                        ActionContext.getContext().getValueStack().push(values);
                }
                return SUCCESS;
        }
        
        
        /**
         * 保存用户
         * 
         * @return 跳转路径
         */
        @Action(value="promotion_save", 
                        results={@Result(name="success", 
                        location="./html/take_delivery/promotion.html", type="redirect")})
        public String promotion_save() {
                String realPath = ServletActionContext.getServletContext().getRealPath("/upload/titleImg");
                File rootPath = new File(realPath);
                if (!rootPath.exists()) {
                       rootPath.mkdirs(); 
                }
                String ext = imgFileFileName.substring(imgFileFileName.lastIndexOf("."));
                UUID uuid = UUID.randomUUID();
                File destFile = new File(realPath, uuid+ext);
                try {
                        FileUtils.copyFile(imgFile, destFile);
                } catch (IOException e) {
                        e.printStackTrace();
                }
                String contextPath = ServletActionContext.getRequest().getContextPath();
                String path = "http://localhost:8889" + contextPath + "/upload/titleImg/" + destFile.getName();
                model.setTitleImg(path);
                // 调用业务层保存
                promotionServiceInter.save(model);
                return SUCCESS;
        }
        
        /**
         * 分页查询促销信息
         * 
         * @return 跳转路径
         */
        @Action(value="promotion_pageQuery", results={@Result(name="success", type="json")})
        public String findPromotions() {
                Pageable pageable = new PageRequest(page -1, rows);
                Page<Promotion> promotions = promotionServiceInter.findPromotions(pageable);
                pushToStack(promotions);
                return SUCCESS;
        }
}