package com.tmw.design.principle.interfacesegregation;
/*
public class Dog implements IAnimalAction {
    @Override
    public void eat() {

    }

    @Override
    public void fly() {
        // Dog can't fly
    }

    @Override
    public void swim() {

    }
}*/

public class Dog implements IEatAnimalAction, ISwimAnimalAction {

    @Override
    public void eat() {

    }

    @Override
    public void swim() {

    }
}
