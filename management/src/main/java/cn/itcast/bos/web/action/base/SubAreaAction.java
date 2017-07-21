package cn.itcast.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.service.inter.SubAreaServiceInter;
import cn.itcast.bos.web.action.common.BaseAction;

@Controller
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/")
public class SubAreaAction extends BaseAction<SubArea>{

        private static final long serialVersionUID = 1L;
        
        @Resource
        private SubAreaServiceInter subAreaServiceImpl;
        
        // 文件上传
        private File file;
        
        private String fileFileName;
        
        public void setFile(File file) {
                this.file = file;
        }

        public void setFileFileName(String fileFileName) {
                this.fileFileName = fileFileName;
        }

        /**
         * 导出分区数据
         */
        @Action(value="exportSubAreaData")
        public void exportSubAreaData() {
                List<SubArea> subAreas = subAreaServiceImpl.findAllSubAreas();
                XSSFWorkbook xworkbook = new XSSFWorkbook();
                XSSFSheet xsheet = xworkbook.createSheet("分区数据");
                XSSFRow row = xsheet.createRow(0);
                row.createCell(0).setCellValue("分区编号");
                row.createCell(1).setCellValue("定区编码");
                row.createCell(2).setCellValue("区域编码");
                row.createCell(3).setCellValue("关键字");
                row.createCell(4).setCellValue("起始号");
                row.createCell(5).setCellValue("结束号");
                row.createCell(6).setCellValue("单双号");
                row.createCell(7).setCellValue("位置信息");
                if (subAreas.size() > 0) {
                        // 循环给表单添加信息
                        for (int i=0;i<subAreas.size();i++) {
                                SubArea subArea = subAreas.get(i);
                                row = xsheet.createRow(i+1);
                               row.createCell(0).setCellValue(subArea.getId());
                                row.createCell(1).setCellValue(subArea.getFixedArea().getId());
                                row.createCell(2).setCellValue(subArea.getArea().getId());
                                row.createCell(3).setCellValue(subArea.getKeyWords());
                                row.createCell(4).setCellValue(subArea.getStartNum());
                                row.createCell(5).setCellValue(subArea.getEndNum());
                                row.createCell(6).setCellValue(subArea.getSingle()+"");
                                row.createCell(7).setCellValue(subArea.getAssistKeyWords());
                               }
                }
                
                // 设置两个头一个流
                HttpServletResponse response = ServletActionContext.getResponse();
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;filename=b.xls");
                try {
                        xworkbook.write(response.getOutputStream());
                        xworkbook.close();
                } catch (IOException e) {
                        e.printStackTrace();
                }
                
        }
        /**
         * 导入分区
         * 
         * @return 跳转路径
         */
        @Action(value="importSubArea")
        private String importSubArea() {
                if (fileFileName != null || "".equals(fileFileName) ) {
                        String ext = fileFileName.substring(fileFileName.lastIndexOf("."));
                        XSSFWorkbook xworkbook = null;
                        HSSFWorkbook hworkbook = null;
                        Sheet sheet = null;
                        ArrayList<SubArea> list = new ArrayList<SubArea>();
                        try {
                                if ("xls".equals(ext)) {
                                        xworkbook = new XSSFWorkbook(new FileInputStream(file));
                                        sheet = xworkbook.getSheetAt(0);
                                } else if ("xlsx".equals(ext)) {
                                        hworkbook = new HSSFWorkbook(new FileInputStream(file));
                                        sheet = hworkbook.getSheetAt(0);
                                }
                                for (Row row : sheet) {
                                        SubArea _subArea = new SubArea();
                                        // 如果是第一行就跳过
                                        if (row.getRowNum() == 0) {
                                                continue;
                                        }
                                        _subArea.setId(row.getCell(0).getStringCellValue());
                                        _subArea.getFixedArea().setId(row.getCell(1).getStringCellValue());
                                        _subArea.getArea().setId(row.getCell(2).getStringCellValue());
                                        _subArea.setKeyWords(row.getCell(3).getStringCellValue());
                                        _subArea.setStartNum(row.getCell(4).getStringCellValue());
                                        _subArea.setEndNum(row.getCell(5).getStringCellValue());
                                        _subArea.setSingle(row.getCell(6).getStringCellValue().toCharArray()[0]);
                                        _subArea.setAssistKeyWords(row.getCell(7).getStringCellValue());
                                        list.add(_subArea);
                                }
                                subAreaServiceImpl.importSubArea(list);
                        } catch (FileNotFoundException e) {
                                e.printStackTrace();
                        } catch (IOException e) {
                                e.printStackTrace();
                        } 
                }
                return SUCCESS;
        }
        
        /**
         * 分页查询所有的分区数据
         * 
         * @return 跳转路径
         */
        @Action(value="findSubArea", results={@Result(name="success", type="json")})
        public String findSubArea() {
                Pageable pageable = new PageRequest(page - 1, rows);
                Page<SubArea> subAreas = subAreaServiceImpl.findSubArea(pageable);
                pushToStack(subAreas);
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
         @Action(value="deleteSubArea", 
                         results={@Result(name="success", location="./html/base/sub_area.html", type="redirect")})
        public String deleteArea() {
                String[] del = ids.split("-");
                subAreaServiceImpl.deleteArea(del);
                return SUCCESS;
        }

}
