package com.example.demo.asm;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

import org.objectweb.asm.*;

import static org.objectweb.asm.Opcodes.*;

public class ASM {

    public static void main(String[] args) throws IOException {

        System.out.println();
        ClassWriter classWriter = new ClassWriter(0);
        // 通过visit方法确定类的头部信息
        classWriter.visit(Opcodes.V12,// java版本
                Opcodes.ACC_PUBLIC,// 类修饰符
                "Programmer", // 类的全限定名
                null, "java/lang/Object", null);

        classWriter.visitField(ACC_PRIVATE+ ACC_STATIC, "wcn", "Ljava/lang/String;", null, "wcn").visitEnd();
        classWriter.visitField(ACC_PRIVATE, "cl", "Ljava/lang/String;", null, "cl").visitEnd();
        classWriter.visitField(ACC_PRIVATE, "aa", "Ljava/lang/String;", null, "cl").visitEnd();

        //创建构造函数
        MethodVisitor mv = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>","()V",false);
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        //创建带参构造函数
        MethodVisitor mv2 = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "(Ljava/lang/String;Ljava/lang/String;)V", null, null);
        mv2.visitCode();
        mv2.visitVarInsn(Opcodes.ALOAD, 0);
        mv2.visitVarInsn(Opcodes.ALOAD, 1);

        mv2.visitFieldInsn(Opcodes.PUTFIELD, "Programmer", "wcn", "Ljava/lang/String;");

        mv2.visitVarInsn(Opcodes.ALOAD, 0);
        mv2.visitVarInsn(Opcodes.ALOAD, 2);
//        mv2.visitVarInsn(Opcodes.ALOAD, 3);
        mv2.visitFieldInsn(Opcodes.PUTFIELD, "Programmer", "cl", "Ljava/lang/String;");
//        mv2.visitFieldInsn(Opcodes.PUTFIELD, "Programmer", "cl", "Ljava/lang/String;");
//        mv2.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>","(Ljava/lang/String;Ljava/lang/String;)V",false);
//        mv2.visitFieldInsn(Opcodes.PUTFIELD, "Programmer", "cl", "Ljava/lang/String;");




        


        mv2.visitInsn(Opcodes.RETURN);
        mv2.visitMaxs(5, 5);
        mv2.visitEnd();

        

//        mv = cw.visitMethod(ACC_PUBLIC, setMethodName, setof, null, null);
//        mv.visitCode();
//        mv.visitVarInsn(ALOAD, 0);
//        mv.visitVarInsn(loadAndReturnOf[0], 1);
//        mv.visitFieldInsn(PUTFIELD, className, fieldName, typeof);
//        mv.visitInsn(RETURN);
//        mv.visitMaxs(3, 3);
//        mv.visitEnd();
//
//        mv2.visitCode();
//        mv2.visitVarInsn(Opcodes.ALOAD, 0);
////        mv2.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>","(Ljava/lang/String;)V",false);
//        mv.visitVarInsn(ALOAD, 1);
//        mv.visitFieldInsn(PUTFIELD, "Programmer", "b", "(Ljava/lang/String;)V");
//        mv.visitInsn(RETURN);
//        mv.visitMaxs(3, 3);
//        mv2.visitEnd();


        // 定义code方法
        MethodVisitor methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "code", "()V",
                null, null);
        methodVisitor.visitCode();
        methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out",
                "Ljava/io/PrintStream;");
        methodVisitor.visitLdcInsn("I'm a Programmer,Just Coding.....");
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println",
                "(Ljava/lang/String;)V",false);
        methodVisitor.visitInsn(Opcodes.RETURN);
        methodVisitor.visitMaxs(2, 2);
        methodVisitor.visitEnd();
        classWriter.visitEnd();
        // 使classWriter类已经完成
        // 将classWriter转换成字节数组写到文件里面去
        byte[] data = classWriter.toByteArray();
        File file = new File("D://Programmer.class");
        FileOutputStream fout = new FileOutputStream(file);
        fout.write(data);
        fout.close();
    }



}
