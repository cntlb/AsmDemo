package com.example.asm.modify;

import com.example.Const;
import com.example.Util;
import com.example.asm.UsingGeneratedClass;
import com.example.java_demo.Hello;
import com.example.java_demo.ISavable;
import com.example.java_demo.IShowable;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static org.objectweb.asm.Opcodes.*;

/**
 * {@link Hello}添加成员
 */
public class AddClassMembers {

    public static void main(String[] args) throws Exception {
        ClassReader cr = new ClassReader(Const.CLASS_HELLO);
        ClassWriter cw = new ClassWriter(0);
        AddMethodAdapter adapter = new AddMethodAdapter(cw, "save", "()V");
        cr.accept(adapter, 0);

        Util.saveClass(cw, "com/example/java_demo/Hello4.class");

        Class aClass = Util.defineClass(null, cw.toByteArray());
//        Class aClass = Class.forName("com.example.java_demo.Hello4");

        Object o = aClass.newInstance();
        Object proxy = Proxy.newProxyInstance(Util.getClassLoader(), aClass.getInterfaces(), new InvocationHandler() {
            Method save = ISavable.class.getMethod("save");

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.equals(save)) {
                    System.out.println(o.getClass() + ":save()");
                    return null;
                }
                return method.invoke(o, args);
            }
        });
        proxy = o;
        aClass.getMethod("test").invoke(o);
        ((IShowable) proxy).show();
        ((ISavable) proxy).save();
    }

    static class AddMethodAdapter extends ClassVisitor {
        String methodName;
        String methodDesc;
        boolean isMethodExists;

        AddMethodAdapter(ClassVisitor cv, String methodName, String methodDesc) {
            super(ASM5, cv);
            this.methodName = methodName;
            this.methodDesc = methodDesc;
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            String[] newInts = new String[interfaces == null ? 1 : interfaces.length + 1];
            if (interfaces != null)
                System.arraycopy(interfaces, 0, newInts, 1, interfaces.length);
            //增加接口后强转后直接调用接口方法, 比反射简便
            newInts[0] = "com/example/java_demo/ISavable";
            super.visit(version, access, "Hello4", signature, superName, newInts);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (name.equals(methodName) && desc.equals(methodDesc))
                isMethodExists = true;
            return super.visitMethod(access, name, desc, signature, exceptions);
        }

        @Override
        public void visitEnd() {
            if (!isMethodExists) {
                cv.visitField(ACC_PUBLIC, "name", "Ljava/lang/String;", null, null)
                        .visitEnd();
                // when statement below uncomment, throw exception:
                //  java.lang.ClassFormatError: Absent Code attribute in method that is not native or abstract in class file Hello4
                // why? Because the method added is broken
                // comment below and use Proxy to implement ISavable.save
//                cv.visitMethod(ACC_PUBLIC, methodName, methodDesc, null, null)
//                        .visitEnd();

                // code from execute:
                // java -classpath asm-all-6.0_BETA.jar:java_demo/build/classes/java/main org.objectweb.asm.util.ASMifier com.example.java_demo.Hello
                MethodVisitor mv;
                mv = cv.visitMethod(ACC_PUBLIC, "save", "()V", null, null);
                mv.visitCode();
                mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
                mv.visitInsn(DUP);
                mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
                mv.visitVarInsn(ALOAD, 0);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;", false);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getName", "()Ljava/lang/String;", false);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                mv.visitLdcInsn(":save()");
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
                mv.visitInsn(RETURN);
                mv.visitMaxs(3, 1);
                mv.visitEnd();
            }
            super.visitEnd();
        }
    }

}
