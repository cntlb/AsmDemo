package com.example.asm;

import com.example.Util;

import org.objectweb.asm.ClassWriter;
import static org.objectweb.asm.Opcodes.*;

public class UsingGeneratedClass {

    public static void main(String[] args) throws Exception {
        ClassWriter cw = new ClassWriter(0);
        cw.visit(V1_7, ACC_PUBLIC|ACC_ABSTRACT|ACC_INTERFACE, "IActivity", null,
                "java/lang/Object", null);
        cw.visitField(ACC_PUBLIC|ACC_STATIC|ACC_FINAL, "TAG", "Ljava/lang/String;", null, "IActivity")
                .visitEnd();
        cw.visitMethod(ACC_PUBLIC|ACC_ABSTRACT, "onCreate", "()V", null, null)
                .visitEnd();
        cw.visitEnd();
        Class iActivity = Util.defineClass("IActivity", cw.toByteArray());
        System.out.println(iActivity);
    }
}
