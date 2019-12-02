package com.example.asm.umeng;

import com.example.Util;
import org.objectweb.asm.*;

import java.io.IOException;

import static org.objectweb.asm.Opcodes.*;


public class UMInject {

    public static void main(String[] args) throws Exception {
        Util.saveClass(referHackWhenInit(), "com/umeng/commonsdk/utils/UMUtils2.class");
    }

    private static byte[] referHackWhenInit() throws IOException {
        ClassReader cr = new ClassReader("com.umeng.commonsdk.utils.UMUtils");
        ClassWriter cw = new ClassWriter(0);
        ClassVisitor cv = new UMUtilsAdapter(ASM5, cw);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    static class UMUtilsAdapter extends ClassVisitor {

        UMUtilsAdapter(int api, ClassVisitor cv) {
            super(api, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
            //public static String getChannelByXML(Context paramContext);
            if (name.equals("getChannelByXML") && desc.equals("(Landroid/content/Context;)Ljava/lang/String;")) {
                System.out.println("inject `public static String getChannelByXML(Context)`");

//                mv.visitCode();
//
//                mv.visitVarInsn(ALOAD, 0);
//                mv.visitMethodInsn(INVOKESTATIC, "com/nurseryrhyme/umeng/analytics/UMInject",
//                        "getChannelByXML", "(Landroid/content/Context;)Ljava/lang/String;", false);
//                mv.visitVarInsn(ASTORE, 1);
//                mv.visitVarInsn(ALOAD, 1);
//                Label l0 = new Label();
//                mv.visitJumpInsn(IFNULL, l0);
//                mv.visitVarInsn(ALOAD, 1);
//                mv.visitInsn(ARETURN);
//                mv.visitLabel(l0);
//                mv.visitFrame(F_APPEND, 1, new Object[]{"java/lang/String"}, 0, null);
//
//                mv.visitInsn(ACONST_NULL);
//                mv.visitInsn(ARETURN);
//                mv.visitMaxs(1, 2);
//                mv.visitEnd();
//                return null;
                return new GetChannelVisitor(api, mv);
            }
            return mv;
        }
    }

    static class GetChannelVisitor extends MethodVisitor {

        GetChannelVisitor(int api, MethodVisitor mv) {
            super(api, mv);
        }

        @Override
        public void visitCode() {
            super.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESTATIC, "com/nurseryrhyme/umeng/analytics/UMInject",
                    "getChannelByXML", "(Landroid/content/Context;)Ljava/lang/String;", false);
            mv.visitVarInsn(ASTORE, 1);
            mv.visitVarInsn(ALOAD, 1);
            Label l0 = new Label();
            mv.visitJumpInsn(IFNULL, l0);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitInsn(ARETURN);
            mv.visitLabel(l0);
            mv.visitFrame(F_APPEND, 1, new Object[]{"java/lang/String"}, 0, null);
        }
    }

}
