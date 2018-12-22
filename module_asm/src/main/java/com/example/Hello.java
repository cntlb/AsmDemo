package com.example;

import com.example.java_demo.IShowable;

public class Hello implements IShowable {
    public void test() {
        System.out.println(getClass().getName() + ":test()");
    }

    public void show() {
        System.out.println(this.getClass().getName() + ":show()");
    }
}
