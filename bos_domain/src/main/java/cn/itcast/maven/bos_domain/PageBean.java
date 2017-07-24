package cn.itcast.maven.bos_domain;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.apache.poi.ss.formula.functions.T;

/**
 * 分页查询的实体类
 *
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月17日  下午2:50:30
 * @param <T>
 */
@SuppressWarnings("hiding")
@XmlRootElement(name="pageBean")
@XmlSeeAlso(Promotion.class)
public class PageBean<T> {

        @Override
        public String toString() {
                return "PageBean [currentPage=" + currentPage + ", pageSize=" + pageSize + ", totalCount=" + totalCount
                                + ", totalPages=" + totalPages + ", pageContent=" + pageContent + "]";
        }

        private Integer currentPage;
        
        private Integer pageSize;
        
        private Integer totalCount;
        
        private Integer totalPages;
        
        private List<T> pageContent;

        public Integer getCurrentPage() {
                return currentPage;
        }

        public Integer getPageSize() {
                return pageSize;
        }

        public Integer getTotalCount() {
                return totalCount;
        }

        public Integer getTotalPages() {
                return totalPages;
        }

        public List<T> getPageContent() {
                return pageContent;
        }

        public void setCurrentPage(Integer currentPage) {
                this.currentPage = currentPage;
        }

        public void setPageSize(Integer pageSize) {
                this.pageSize = pageSize;
        }

        public void setTotalCount(Integer totalCount) {
                this.totalCount = totalCount;
        }

        public void setTotalPages(Integer totalPages) {
                this.totalPages = totalPages;
        }

        public void setPageContent(List<T> pageContent) {
                this.pageContent = pageContent;
        }
}
