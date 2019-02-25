package com.tmw.design.principle.openclose;

public class JavaDiscountCourse extends JavaCourse {

    public JavaDiscountCourse(Integer id, String name, Double price) {
        super(id, name, price);
    }

    public Double getDiscountPrice() {
        return super.getPrice() * 0.8;
    }

    /*
    public Double getOrginalPrice() {
        return super.getPrice();
    }*/

    /**
     * 已经违背了里氏替换原则
     * @return
    @Override
    public Double getPrice() {
        return super.getPrice() * 0.8;
    }
     */
}
