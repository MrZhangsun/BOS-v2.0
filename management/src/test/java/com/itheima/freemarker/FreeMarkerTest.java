package com.itheima.freemarker;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import org.apache.tomcat.jni.File;
import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

/**
 * freemarker的入门
 *
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月17日  下午9:56:58
 */
public class FreeMarkerTest {

        @Test
        public void freemarker1() throws IOException, TemplateException {
                // 获取文件路径
                Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);
                configuration.setDirectoryForTemplateLoading(new java.io.File("src/main/webapp/WEB-INF/template"));
                // 获取模板
                Template template = configuration.getTemplate("hello.ftl");
                // 配置动态数据
                HashMap<Object, Object> map = new HashMap<>();
                map.put("title", "zhangsan");
                map.put("name", "lisi");
                // 将动态数据绑定到模板
                template.process(map, new PrintWriter(System.out));
        }
}
