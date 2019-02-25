package com.tmw.design.principle.denpendencyinversion;


public class Echo {

    /** 这个对比以下的方法，既是面向细节编程
    public void studyJavaCourse() {
        System.out.println("Echo 在学习 Java");
    }

    public void studyFECourse() {
        System.out.println("Echo 在学习 FE");
    }

    public void studyPythonCourse() {
        System.out.println("Echo 在学习 Python");
    }
     */

    private ICourse iCourse;

    public Echo(ICourse iCourse) {
        this.iCourse = iCourse;
    }
    public void studyImoocCourse() {
        this.iCourse.studyCourse();
    }
    public void setiCourse(ICourse iCourse) {
        this.iCourse = iCourse;
    }


}
