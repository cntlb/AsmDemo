package com.example.asm.tools;

import org.objectweb.asm.Type;

import java.util.Arrays;

public class TypeTest {
    public static void main(String[] args) throws Exception {
        System.out.println(Type.getType(String.class).getInternalName());
        System.out.println(Type.getType(String.class).getDescriptor());
        System.out.println(Type.getType(String.class.getMethod("indexOf", int.class, int.class)).getDescriptor());
        System.out.println(Arrays.toString(Type.getArgumentTypes("(IIZZBC)I")));
        System.out.println(Type.getReturnType("()V") == Type.VOID_TYPE);
    }

}
