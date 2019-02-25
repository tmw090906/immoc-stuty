package com.tmw.design.principle.liskovsubstitution;

public class Square implements Quadrangle {

    private int sideLength;

    public int getSideLength() {
        return sideLength;
    }

    public void setSideLength(int sideLength) {
        this.sideLength = sideLength;
    }

    @Override
    public int getLength() {
        return this.sideLength;
    }

    @Override
    public int getWidth() {
        return this.sideLength;
    }

}
