package com.tmw.design.principle.denpendencyinversion;

public class Test {

    public static void main(String[] args) {
        /* v2
        Echo echo = new Echo();

        echo.studyImoocCourse(new FECourse());
        echo.studyImoocCourse(new PythonCourse());
        echo.studyImoocCourse(new JavaCourse());
        */

        /* v3
        Echo echo = new Echo(new PythonCourse());
        echo.studyImoocCourse();
        */

        Echo echo = new Echo(new PythonCourse());
        echo.studyImoocCourse();

        echo.setiCourse(new FECourse());
        echo.studyImoocCourse();
    }

}
