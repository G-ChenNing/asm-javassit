package com.example.demo.asm.third.asm3;

import com.example.demo.asm.third.AccountImpl;
import com.example.demo.asm.third.service.Account;
import org.objectweb.asm.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class asmAop extends ClassLoader {
    static class AddSecurityCheckClassAdapter extends ClassVisitor {

        public AddSecurityCheckClassAdapter(ClassVisitor cv) {
            //Responsechain 的下一个 ClassVisitor，这里我们将传入 ClassWriter，
            // 负责改写后代码的输出
            super(Opcodes.ASM7, cv);
        }

        // 重写 visitMethod，访问到 "operation" 方法时，
        // 给出自定义 MethodVisitor，实际改写方法内容
        @Override
        public MethodVisitor visitMethod(final int access, final String name,
                                         final String desc, final String signature, final String[] exceptions) {
            MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
            MethodVisitor wrappedMv = mv;
            if (mv != null) {
                // 对于 "operation" 方法
                if (name.equals("operation")) {
                    // 使用自定义 MethodVisitor，实际改写方法内容
                    wrappedMv = new AddSecurityCheckMethodAdapter(mv);
                }
            }
            return wrappedMv;
        }
    }

    static class AddSecurityCheckMethodAdapter extends MethodVisitor {
        public AddSecurityCheckMethodAdapter(MethodVisitor mv) {
            super(Opcodes.ASM7, mv);
        }

        @Override
        public void visitCode() {
            Label l0 = new Label();
            visitLabel(l0);
            visitLineNumber(24, l0);
//            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//
//            mv.visitLdcInsn("Hello Aop");
//
//            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V",false);
            visitMethodInsn(Opcodes.INVOKESTATIC, "com/example/demo/asm/third/SecurityChecker",
                    "checkSecurity", "()V", false);
            Label l1 = new
                    Label();

            mv.visitLabel(l1);
            mv.visitLineNumber(25, l1);
            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");

            mv.visitLdcInsn("Hello Aop");

            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V",false);

//            Label l4 = new
//                    Label();
//
//            mv.visitLabel(l4);
//
//            mv.visitLocalVariable("this", "Lorg/more/test/asm/TestBean;", null, l0, l4, 0);



        }

        @Override
        public void visitInsn(int opcode) {
            if (opcode == Opcodes.RETURN) {
                Label l2 = new
                        Label();

                mv.visitLabel(l2);

                mv.visitLineNumber(25, l2);
//                mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//
//                mv.visitLdcInsn("Hello Aop");
//
//                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V",false);
                visitMethodInsn(Opcodes.INVOKESTATIC, "com/example/demo/asm/third/SecurityChecker",
                        "aftercheckSecurity", "()V", false);
            }
            super.visitInsn(opcode);
        }
    }

    public  Class<?> aop() throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        ClassReader cr = new ClassReader("com.example.demo.asm.third.AccountImpl");
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ClassVisitor classAdapter = new AddSecurityCheckClassAdapter(cw);
        cr.accept(classAdapter, ClassReader.SKIP_DEBUG);

        byte[] data = cw.toByteArray();

//        Class<?> account = this.defineClass("AccountImpl", data, 0, data.length);
//        Account o = (Account)account.getDeclaredConstructor().newInstance();

        File file = new File("D://AccountImpl.class");
        FileOutputStream fout = new FileOutputStream(file);
        fout.write(data);
        fout.close();

        Class<?> exampleClass = this.defineClass("com.example.demo.asm.third.AccountImpl", data, 0, data.length);
        return exampleClass;

    }

    public static void main(String[] args) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        asmAop asmAop = new asmAop();
        Class<?> aop = asmAop.aop();
//        for(Method method : aop.getMethods()){
//            System.out.println(method);
//        }
        Account o = (Account)aop.getDeclaredConstructor().newInstance();
        o.operation();


    }
}
