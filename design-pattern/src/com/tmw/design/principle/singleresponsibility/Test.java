package com.tmw.design.principle.singleresponsibility;

public class Test {

    public static void main(String[] args) {

        /* v1
        Bird bird = new Bird();

        bird.mainMoveMode("大雁");
        bird.mainMoveMode("鸵鸟");
        */

        FlyBird flyBird = new FlyBird();
        flyBird.mainMoveMode("大雁");

        WalkBird walkBird = new WalkBird();
        walkBird.mainMoveMode("鸵鸟");
    }

}
