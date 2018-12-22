package com.example;

import java.io.File;
import java.io.FileOutputStream;

import org.objectweb.asm.ClassWriter;

public class Util {
    private static final MyClassLoader INSTANT = new MyClassLoader();

    private Util() {
    }

    private static class MyClassLoader extends ClassLoader {
        Class defineClass(String name, byte[] b) {
            return defineClass(name, b, 0, b.length);
        }
    }

    public static ClassLoader getClassLoader() {
        return INSTANT;
    }

    public static Class defineClass(String name, byte[] b) {
        return INSTANT.defineClass(name, b);
    }

    public static void saveClass(byte[] bytes, String destName) throws Exception {
        File file = new File(Const.OUT_DIR, destName).getCanonicalFile();
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bytes);
        fos.close();
    }

    public static void saveClass(ClassWriter cw, String destName) throws Exception {
        File file = new File(Const.OUT_DIR, destName).getCanonicalFile();
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(cw.toByteArray());
        fos.close();
    }
}
