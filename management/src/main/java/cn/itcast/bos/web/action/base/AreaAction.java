package cn.itcast.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.base.inter.AreaServiceInter;
import cn.itcast.bos.util.PinYin4jUtils;
import cn.itcast.bos.web.action.common.BaseAction;

/**
 * 区域模块表示层
 * 
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月4日  下午11:11:40
 */
@Controller
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/")
@Actions
public class AreaAction extends BaseAction<Area>{

        private static final long serialVersionUID = 1L;
        
        @Resource
        private AreaServiceInter areaServiceInter;
       
        /** 上传文件对象 */
        private File file;
        public void setFile(File file) {
                this.file = file;
        }
        /**
         * 文件上传
         * 
         * @return 
         */
        @Action(value="uploadFile", results={@Result(name="success",
                        location="./html/base/area.html", type="redirect")})
        public String uploadFile() {
                XSSFWorkbook workbook;
                try {
                        workbook = new XSSFWorkbook(new FileInputStream(file));
                        XSSFSheet worksheet = workbook.getSheetAt(0);
                        ArrayList<Area> list = new ArrayList<Area>();
                        for (Row row : worksheet) {
                                // 如果是第一行就跳过
                                if (row.getRowNum() == 0) {
                                        continue;
                                }
                                Area _area = new Area();
                                _area.setId(row.getCell(0).getStringCellValue());
                                _area.setProvince(row.getCell(1).getStringCellValue());
                                _area.setCity(row.getCell(2).getStringCellValue());
                                _area.setDistrict(row.getCell(3).getStringCellValue());
                                _area.setPostcode(row.getCell(4).getStringCellValue());
                                // 城市简码
                                String province = row.getCell(1).getStringCellValue();
                                String city = row.getCell(2).getStringCellValue();
                                String district = row.getCell(3).getStringCellValue();
                                province = province.substring(0, province.length() - 1);
                                city = city.substring(0, city.length() - 1);
                                district = district.substring(0, district.length() - 1);
                                String[] headByString = PinYin4jUtils.getHeadByString(province + city + district);
                                StringBuffer buffer = new StringBuffer();
                                for(String str : headByString) {
                                        buffer.append(str);
                                }
                                _area.setShortcode(buffer.toString());
                                //城市编码
                               _area.setCitycode(PinYin4jUtils.convertHeadToUpperCase(city));
                                list.add(_area);
                        }
                        // 调用业务层去保存数据
                        areaServiceInter.saveAreaInfo(list);
                } catch (IOException e) {
                        e.printStackTrace();
                }
                return SUCCESS;
        }

        /**
         * 条件查询所有的区域信息
         * 
         * @return 跳转路径
         */
        @Action(value="pageQueryArea", results={@Result(name="success",  type="json")})
        public String pageQueryArea() {
                Pageable pageable = new PageRequest(page - 1, rows);
                Specification<Area> specification = new Specification<Area>() {

                        @Override
                        public Predicate toPredicate(Root<Area> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                                // 创建一个list集合存条件
                                ArrayList<Predicate> list = new ArrayList<Predicate>();
                                // 重构条件
                                if (StringUtils.isNotBlank(model.getProvince())) {
                                        // 根据省模糊查询
                                        Predicate p1 = cb.like(root.get("province").as(String.class), "%"+model.getProvince()+"%");
                                        list.add(p1);
                                }
                                if (StringUtils.isNotBlank(model.getCity())) {
                                        Predicate p2 = cb.like(root.get("city").as(String.class), "%"+model.getCity()+"%");
                                        list.add(p2);
                                }
                                
                                if (StringUtils.isNotBlank(model.getDistrict())) {
                                        Predicate p3 = cb.like(root.get("district").as(String.class), "%"+model.getDistrict()+"%");
                                        list.add(p3);
                                }
                                
                                return cb.and(list.toArray(new Predicate[0]));
                        }
                };
                Page<Area> areas = areaServiceInter.pageQueryArea(specification, pageable);
                // 将数据压入到值栈中去
                pushToStack(areas);
                return SUCCESS;
        }

        /**
         * 添加或更新区域信息
         * 
         * @return 跳转路径
         */
        @Action(value="saveOrUpdateArea", 
                        results={@Result(name="success", location="./html/base/area.html", type="redirect")})
        public String saveOrUpdateArea() {
                // 获取区域信息
                String province = model.getProvince();
                String city = model.getCity();
                String district = model.getDistrict();
                // 简码
                province  = province.substring(0,province.length() - 1);
                city = city.substring(0, city.length() - 1);
                district = district.substring(0, district.length() - 1);
                String[] shortCode = PinYin4jUtils.getHeadByString(province + city + district);
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < shortCode.length; i++) {
                        buffer.append(shortCode[i]);
                }
                model.setShortcode(buffer.toString());
                // 城市编码
                model.setCitycode(PinYin4jUtils.convertHeadToUpperCase(city));
                areaServiceInter.saveOrUpdateArea(model);
                return SUCCESS;
        }
        
        /**
         * 查询指定区域的信息
         * 
         */
        @Action(value="findArea", results={@Result(name="success", type="json")})
        public String findArea() {
                Area _area = areaServiceInter.findOne(model.getId());
                // 将查询到数据压入到栈顶
                ActionContext.getContext().getValueStack().push(_area);
                return SUCCESS;
        }
        
        private String ids;

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
                areaServiceInter.deleteArea(del);
                return SUCCESS;
        }
}
