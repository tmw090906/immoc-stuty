package com.tmw.design.pattern.creational.builder.v2;

public class Test {
    public static void main(String[] args) {
        Course course = Course.builder()
                .builderCourseArticle("Python课程Article")
                .builderCourseName("Python课程")
                .builderCoursePPT("Python课程PPT")
                .builderCourseQA("Python课程QA")
                .builderCourseVideo("Python课程Video")
                .build();
        System.out.println(course);
    }
}
