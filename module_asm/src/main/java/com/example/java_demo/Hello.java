package com.example.java_demo;

public class Hello implements IShowable {
    public void test() {
        System.out.println(getClass().getName() + ":test()");
    }

    public void show() {
        System.out.println(this.getClass().getName() + ":show()");
    }
}
