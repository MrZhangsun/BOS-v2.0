package com.itheima.annotation;

public class Annotation implements AnnotationInter {

        @MyAnnotaction(value="dde", name=String.class,results=("ss"))
        public void save() {
                System.out.println("my annotation exeute");
        }
        public static void main(String[] args) {
                String string = "dded";
                String[] d = string.split("-");
                System.out.println(d[0]);
        }
}
