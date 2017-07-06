package com.itheima.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义一个注解
 * 基本数据类型:
 * String类型:
 * Class类型:
 * 注解类型:
 * 枚举类型:
 * 以上类型的一维数组:

 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月2日  上午10:37:53
 */
@Target(ElementType.METHOD) //指定注解标注的位置
@Retention(RetentionPolicy.CLASS)//指定注解执行的时期SOURCE:编译期, runtime:运行期
public @interface MyAnnotaction {
        /**
         * 注解实质上是以个接口
         * 注解中地属性实质上是接口中的抽象方法
         * 所以属性的定义是根据方法的定义去定义
         */
        public String value();
        String results();
        Class<String> name();
        
}
