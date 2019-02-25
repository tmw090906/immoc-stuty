package com.tmw.design.principle.denpendencyinversion;

public class FECourse implements ICourse {
    @Override
    public void studyCourse() {
        System.out.println("Echo 在学习 FE");
    }
}
