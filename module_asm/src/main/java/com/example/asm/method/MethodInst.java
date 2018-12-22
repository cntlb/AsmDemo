package com.example.asm.method;

import com.example.Util;

import org.objectweb.asm.*;

import static org.objectweb.asm.Opcodes.*;

/**
 * 创建一个完整的java类:
 * <pre>
 * public class Bean {
 *     private int f;
 *
 *     public Bean() {
 *     }
 *
 *     public int getF() {
 *         return this.f;
 *     }
 *
 *     public void setF(int f) {
 *         this.f = f;
 *     }
 *
 *     public void checkAndSet(int f) {
 *         if (var1 >= 0) {
 *             this.f = f;
 *         } else {
 *             throw new IllegalArgumentException();
 *         }
 *     }
 * }
 * </pre>
 */
public class MethodInst {
    public static void main(String[] args) throws Exception {
        ClassWriter cw = new ClassWriter(0);
        //public class Bean
        cw.visit(V1_7, ACC_PUBLIC, "Bean", null, "java/lang/Object", null);

        //private int f;
        FieldVisitor fv;
        fv = cw.visitField(ACC_PRIVATE, "f", "I", null, null);
        fv.visitEnd();

        MethodVisitor mv;
        //public Bean()
        mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        //public int getF()
        mv = cw.visitMethod(ACC_PUBLIC, "getF", "()I", null, null);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD, "Bean", "f", "I");
        mv.visitInsn(IRETURN);
        mv.visitMaxs(1,1);
        mv.visitEnd();

        //public void setF(int f)
        mv = cw.visitMethod(ACC_PUBLIC, "setF", "(I)V", null, null);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(ILOAD, 1);
        mv.visitFieldInsn(PUTFIELD, "Bean", "f", "I");
        mv.visitInsn(RETURN);
        mv.visitMaxs(2,2);
        mv.visitEnd();

        //public void checkAndSet(int f)
        mv = cw.visitMethod(ACC_PUBLIC, "checkAndSet", "(I)V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ILOAD, 1);
        Label l0 = new Label();
        mv.visitJumpInsn(IFLT, l0);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(ILOAD, 1);
        mv.visitFieldInsn(PUTFIELD, "Bean", "f", "I");
        Label l1 = new Label();
        mv.visitJumpInsn(GOTO, l1);
        mv.visitLabel(l0);
        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        mv.visitTypeInsn(NEW, "java/lang/IllegalArgumentException");
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/IllegalArgumentException", "<init>", "()V", false);
        mv.visitInsn(ATHROW);
        mv.visitLabel(l1);
        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        mv.visitInsn(RETURN);
        mv.visitMaxs(2, 2);
        mv.visitEnd();

        cw.visitEnd();
        Util.saveClass(cw.toByteArray(), "Bean.class");
        Class ClassBean = Util.defineClass("Bean", cw.toByteArray());
        Object bean = ClassBean.newInstance();
        System.out.println(bean);

        ClassBean.getMethod("setF", int.class).invoke(bean, 20);
        ClassBean.getMethod("checkAndSet", int.class).invoke(bean, 020);
        System.out.println(ClassBean.getMethod("getF").invoke(bean));
    }

}
