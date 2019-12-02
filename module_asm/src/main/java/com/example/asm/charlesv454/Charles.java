package com.example.asm.charlesv454;

import com.example.Util;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

/**
 * 使用asm框架修改 Charles 4.5.4验证代码以破解
 */
public class Charles {

    public static void main(String[] args) throws Exception {
        //注意确保 "com.xk72.charles.DIWy" 在classpath中, 可以拷贝到class输出目录

        ClassReader cr = new ClassReader("com.xk72.charles.DIWy");
        ClassWriter cw = new ClassWriter(0);
        CharlesAdapter adapter = new CharlesAdapter(ASM5, cw);
        cr.accept(adapter, ASM5);

        Util.saveClass(cw.toByteArray(), "com/xk72/charles/DIWy2.class");
    }

    static class CharlesAdapter extends ClassVisitor {
        public CharlesAdapter(int i, ClassVisitor classVisitor) {
            super(i, classVisitor);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
            if (name.equals("OZtq") && "()Z".equals(desc)) {
                mv.visitCode();
                mv.visitInsn(ICONST_1);
                mv.visitInsn(IRETURN);
                mv.visitMaxs(1, 0);
                mv.visitEnd();
                return null;
            }
            if (name.equals("wkKg") && desc.equals("()Ljava/lang/String;")) {
                mv.visitCode();
                mv.visitLdcInsn("tlb@jmu");
                mv.visitInsn(ARETURN);
                mv.visitMaxs(1, 0);
                mv.visitEnd();
                return null;
            }
            return mv;
        }
    }
}
