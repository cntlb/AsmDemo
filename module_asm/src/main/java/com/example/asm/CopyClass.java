package com.example.asm;

import com.example.Const;
import com.example.Util;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

public class CopyClass {

    public static void main(String[] args) throws Exception {
        ClassReader cr = new ClassReader(Const.CLASS_HELLO);
        ClassWriter cw = new ClassWriter(0);
        cr.accept(cw, ClassReader.EXPAND_FRAMES);
        Util.saveClass(cw, "Hello2.class");
    }
}
