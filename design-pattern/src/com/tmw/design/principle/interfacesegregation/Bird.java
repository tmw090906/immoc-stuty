package com.tmw.design.principle.interfacesegregation;

/*
public class Bird implements IAnimalAction {
    @Override
    public void eat() {

    }

    @Override
    public void fly() {

    }

    @Override
    public void swim() {
        // normal bird can't fly
    }
}*/

public class Bird implements IFlyAnimalAction, IEatAnimalAction {
    @Override
    public void eat() {

    }

    @Override
    public void fly() {

    }

}
