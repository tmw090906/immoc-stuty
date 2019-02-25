package com.tmw.design.principle.singleresponsibility;

/**
 * 接口单一原则
 */
public interface ICourse {

    /**
     * 下面两个方法： 属于课程信息职责部分： 把他分到了ICouseContent中
     */
    String getCourseName();
    byte[] getCourseVideo();

    /**
     * 下面两个方法： 属于课程管理职责部分： 把他分到了ICouseManager中
     */
    void studyCourse();
    void refundCourse();

}
