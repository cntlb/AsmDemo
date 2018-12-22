package com.example.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ParsingClass {

    public static void main(String[] args) throws Exception {
        // 运行GeneratingClass 先生成 pkg.Comparable
        ClassReader cr = new ClassReader("pkg.Comparable");
        cr.accept(new ClassPrinter(), 0);
    }

    static class ClassPrinter extends ClassVisitor {
        ClassPrinter() {
            super(Opcodes.ASM5);
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            super.visit(version, access, name, signature, superName, interfaces);
            System.out.println("class " + name + " extends " + superName + " {");
        }

        @Override
        public void visitSource(String source, String debug) {
            super.visitSource(source, debug);
            System.out.println("    // source file: " + source);
        }

        @Override
        public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
            System.out.println("    "+desc+" "+name +" = "+value);
            return null;
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            System.out.println("    " + name + " " + desc);
            return null;
        }

        @Override
        public void visitEnd() {
            super.visitEnd();
            System.out.println("}");
        }
    }

}
