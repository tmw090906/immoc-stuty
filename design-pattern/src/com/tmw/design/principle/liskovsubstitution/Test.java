package com.tmw.design.principle.liskovsubstitution;

public class Test {

    public static void main(String[] args) {
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(10);
        rectangle.setLength(20);
        resize(rectangle);

        Square square = new Square();
        square.setSideLength(10);
        //resize(square);
    }

    /**
     * 在此方法中，Square虽然是Rectangle的子类，但是在resize()中，行为不一样
     * 故违反了里氏替换原则(liskov substitution)
     * @param rectangle
     */
    public static void resize(Rectangle rectangle) {
        while (rectangle.getWidth() <= rectangle.getLength()) {
            rectangle.setWidth(rectangle.getWidth() + 1);
            System.out.println("width:" + rectangle.getWidth() + ",length:" + rectangle.getLength());
        }
        System.out.println("resize 方法结束 width:" + rectangle.getWidth() + ",length:" + rectangle.getLength());
    }

}
