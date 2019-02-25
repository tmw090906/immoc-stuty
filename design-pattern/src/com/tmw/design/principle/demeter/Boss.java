package com.tmw.design.principle.demeter;

public class Boss {

    public void commandCheckNumber(TeamLeader teamLeader) {
        // Course 不是 Boss的朋友类
        /*
        List<Course> courseList = new ArrayList();
        for (int i = 0 ; i < 20 ; i++) {
            courseList.add(new Course());
        }
        */
        teamLeader.CheckNumberOfCourse();
    }

}
