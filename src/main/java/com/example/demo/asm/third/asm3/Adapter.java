package com.example.demo.asm.third.asm3;

import org.objectweb.asm.*;

public class Adapter {

    /**
     * 删除类的字段、方法、指令：只需在职责链传递过程中中断委派，不访问相应的 visit 方法即可，比如删除方法时只需直接返回 null，而不是返回由 visitMethod方法返回的 MethodVisitor对象。
     */
    public static class DelLoginClassAdapter extends ClassVisitor {
        public DelLoginClassAdapter(ClassVisitor cv) {
            super(Opcodes.ASM7, cv);
        }

        @Override
        public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
            if (name.equals("name")) {
                return null;
            }
            return super.visitField(access, name, descriptor, signature, value);
        }

        @Override
        public MethodVisitor visitMethod(final int access, final String name,
                                         final String desc, final String signature, final String[] exceptions) {
            if (name.equals("name")) {
                return null;
            }
            return cv.visitMethod(access, name, desc, signature, exceptions);
        }
    }

    /**
     * 修改类、字段、方法的名字或修饰符：在职责链传递过程中替换调用参数。
     */
    public static class AccessClassAdaptor extends ClassVisitor {
        public AccessClassAdaptor(ClassVisitor cv) {
            super(Opcodes.ASM7, cv);
        }

        @Override
        public FieldVisitor visitField(final int access, final String name,
                                       final String desc, final String signature, final Object value) {
            int privateAccess = Opcodes.ACC_PRIVATE;
            return cv.visitField(privateAccess, name, desc, signature, value);
        }
    }

    public static void main(String[] args) {
//        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
//        ClassVisitor delLoginClassAdaptor = new DelLoginClassAdapter(classWriter);
//        ClassVisitor accessClassAdaptor = new AccessClassAdaptor(delLoginClassAdaptor);
//
//        ClassReader classReader = new ClassReader(strFileName);
//        classReader.accept(classAdapter, ClassReader.SKIP_DEBUG);
    }
}
