package com.itheima.Fanxing;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Fanxing {
        public static void main(String[] args) {
                @SuppressWarnings("unused")
                A a = new A();
        }
}

/**
 * 被抽取的类
 * 
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月5日  下午6:47:14
 */
class A extends B<String> implements C<Integer> {
        public A() {
                System.out.println("ddd");
        }
}

/**
 * 抽取的基类
 * 
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月5日  下午6:47:39
 * @param <E>
 */
class B<E>{
        public B() {
                Type type = this.getClass().getGenericSuperclass();
                ParameterizedType type2 = (ParameterizedType) type;
                @SuppressWarnings("unchecked")
                Class<E> type3 = (Class<E>) type2.getActualTypeArguments()[0];
                E newInstance;
                try {
                        newInstance = type3.newInstance();
                        System.out.println(newInstance.getClass());
                } catch (InstantiationException | IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
        }
}
interface C<T>{
        
}