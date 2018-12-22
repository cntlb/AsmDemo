package com.example.asm;

import com.example.Const;
import com.example.Util;

import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;

import static org.objectweb.asm.Opcodes.*;

public class GeneratingClass {
    /**
     * Generate class compiled by source below:
     * <pre>
     *      package pkg;
     *      public interface Comparable extends Mesurable {
     *          int LESS = -1;
     *          int EQUAL = 0;
     *          int GREATER = 1;
     *          int compareTo(Object o);
     *      }
     * </pre>
     */
    public static void main(String[] args) throws Exception {
        ClassWriter cw = new ClassWriter(0);
        cw.visit(V1_5, ACC_PUBLIC|ACC_ABSTRACT|ACC_INTERFACE,
                "pkg/Comparable", null, "java/lang/Object",
                new String[] { "pkg/Mesurable" });
        cw.visitField(ACC_PUBLIC | ACC_FINAL | ACC_STATIC, "LESS", "I",
                null, -1).visitEnd();
        cw.visitField(ACC_PUBLIC | ACC_FINAL | ACC_STATIC, "EQUAL", "I",
                null, 0).visitEnd();
        cw.visitField(ACC_PUBLIC | ACC_FINAL | ACC_STATIC, "GREATER", "I",
                null, 1).visitEnd();
        cw.visitMethod(ACC_PUBLIC | ACC_ABSTRACT, "compareTo",
                "(Ljava/lang/Object;)I", null, null).visitEnd();
        cw.visitEnd();

        Util.saveClass(cw, "pkg/Comparable.class");
    }

}
