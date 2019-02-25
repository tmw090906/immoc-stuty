package com.tmw.design.pattern.creational.builder;

public class Test {

    public static void main(String[] args) {
        CourseBuilder builder = new CourseActualBuilder();
        Coach coach = new Coach();
        coach.setCourseBuilder(builder);
        coach.makeCourse("Java设计模式精讲",
                "Java设计模式精讲PPT",
                "Java设计模式精讲Article",
                "Java设计模式精讲Video",
                "Java设计模式精讲QA");
    }

}
