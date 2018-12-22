package com.example;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class UtilTest {
    @Test
    public void test1() throws Exception {
        System.out.println(new File("/").getCanonicalPath());
        System.out.println(new File("./").getCanonicalPath());
        System.out.println(new File("out/production/classes").getCanonicalPath());
        System.out.println(new File("./out/production/classes").getCanonicalPath());

    }
}