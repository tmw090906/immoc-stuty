package com.tmw.design.pattern.creational.abstractfactory;

public class Test {

    public static void main(String[] args) {
        CourseFactory courseFactory = new PythonCourseFactory();
        Video video = courseFactory.getVideo();
        Article article = courseFactory.getArticle();
        video.produce();
        article.produce();
    }

}
