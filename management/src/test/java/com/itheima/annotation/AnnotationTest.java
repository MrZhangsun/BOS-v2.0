package com.itheima.annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.Test;

/**
 * 自定义注解的测试
 * 
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月2日  上午10:53:39
 */
public class AnnotationTest {

        /**
         * 利用动态代理的方式运行注解
         * @throws InvocationTargetException 
         * @throws IllegalArgumentException 
         * @throws IllegalAccessException 
         * @throws SecurityException 
         * @throws NoSuchMethodException 
         */
        @Test
        public void annotationTest() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                Annotation ann = new Annotation();
                Method[] methods = ann.getClass().getMethods();
                for (Method method : methods) {
                        MyAnnotaction annotation = method.getAnnotation(MyAnnotaction.class);
                        if (annotation != null) {
                                method.invoke(ann);
                        }
                }
        }
}
