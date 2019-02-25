package com.tmw.design.principle.openclose;

public class Test {

    public static void main(String[] args) {

        ICourse javaCourse = new JavaDiscountCourse(96, "Java从零到企业级电商开发", 348d);

        System.out.println("课程ID:" + javaCourse.getId() + " 课程名称：" + javaCourse.getName() + " 课程折后价格:" + ((JavaDiscountCourse) javaCourse).getDiscountPrice() + " 课程原价:" + javaCourse.getPrice());

    }


}
