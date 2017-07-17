package cn.itcast.bos.web.action.common;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import org.springframework.data.domain.Page;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 所有action的一个基类
 * 
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月5日  下午6:33:37
 */
public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

        private static final long serialVersionUID = 1L;
        /*
         * 1.抽取模型驱动
         * 2.分页查询数据压栈的方法的抽取
         * (non-Javadoc)
         * @see com.opensymphony.xwork2.ModelDriven#getModel()
         */
        protected T model;
        @Override
        public T getModel() {
                return model;
        }
        
        /**
         * 在构造方法中获取泛型,创建一个model对象
         */
        public BaseAction() {
                Type superclass = this.getClass().getGenericSuperclass();
                ParameterizedType parameterizedType =(ParameterizedType) superclass;
                @SuppressWarnings("unchecked")
                Class<T> type = (Class<T>) parameterizedType.getActualTypeArguments()[0];
                try {
                        model = type.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                        System.out.println("模型封装失败!");
                        e.printStackTrace();
                }
        }
        
        /** 当前页 */
        public int page;
        
        /** 分页单位 */
        public int rows;
        public void setPage(int page) {
                this.page = page;
        }

        public void setRows(int rows) {
                this.rows = rows;
        }

        /**
         * 分页查询结果数据压栈的方法
         * 
         * @param pageData 分页查询的数据
         */
        protected void pushToStack(Page<T> pageData) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("total", pageData.getTotalElements());
                map.put("rows", pageData.getContent());
                ActionContext.getContext().getValueStack().push(map);
        }
}
