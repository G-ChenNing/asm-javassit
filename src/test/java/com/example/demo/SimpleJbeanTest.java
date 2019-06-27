package com.example.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


import com.example.demo.asm.second.FieldInfo;
import com.example.demo.asm.second.SimpleJbean;
import com.example.demo.asm.second.SimpleJbeanAsm;
import com.example.demo.asm.second.TestFieldInfo;
import com.example.demo.asm.third.asm3.Adapter;
import com.example.demo.javassist.second.SimpleJbeanJs;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import static org.assertj.core.api.Java6Assertions.fail;
import static org.junit.Assert.assertEquals;


/**
 * SimpleJbeanTest.java. 2011-12-30下午9:00:09 @author LionBule.
 */
public class SimpleJbeanTest extends ClassLoader {
    private String          className;
    private List<FieldInfo> fields;

    @Before
    public void setUp() throws Exception {
        className = "User";
        FieldInfo testString = new TestFieldInfo(String.class, "name", "lionbule");
        FieldInfo testInt = new TestFieldInfo(int.class, "age", 27);
        FieldInfo testLong = new TestFieldInfo(long.class, "count", 9999999999L);
        FieldInfo testFloat = new TestFieldInfo(float.class, "score", 89.312F);
        FieldInfo testDouble = new TestFieldInfo(double.class, "number", 89.3121313D);
        FieldInfo testBoolean = new TestFieldInfo(Boolean.class, "isStudent", false);

        fields = new ArrayList<FieldInfo>();
        fields.add(testString);
        fields.add(testInt);
        fields.add(testLong);
        fields.add(testFloat);
        fields.add(testDouble);
        fields.add(testBoolean);
    }

//    @Test
//    public void testSimpleJbeanJs() {
//        try {
//            SimpleJbean simpleJbean = new SimpleJbeanJs();
//            doAction(simpleJbean);
//        } catch (Exception e) {
//            e.printStackTrace();
//            fail(e.getMessage());
//        }
//
//    }

    @Test
    public void testSimpleJbeanAsm() {
        try {
            SimpleJbean simpleJbean = new SimpleJbeanAsm();
            doAction(simpleJbean);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

    }

    private void doAction(SimpleJbean simpleJbean) throws Exception {
        byte[] classBytes = simpleJbean.createBeanClass(className, fields);

        Class<?> userClass = this.defineClass(className, classBytes, 0, classBytes.length);

        //invoker
        Object user = userClass.getDeclaredConstructor().newInstance();
        TestFieldInfo tempField = null;
        for (FieldInfo t : fields) {
            tempField = (TestFieldInfo) t;

            String setMethodName = "set" + StringUtils.capitalize(tempField.name);
            String getMethodName = "get" + StringUtils.capitalize(tempField.name);

            Method setMethod = userClass.getMethod(setMethodName, new Class[] { tempField.type });
            setMethod.invoke(user, new Object[] { tempField.value });
            Method getMethod = userClass.getMethod(getMethodName, new Class[] {});
            System.out.println(getMethod.toGenericString());
            Object result = getMethod.invoke(user, new Object[] {});
            assertEquals(tempField.value, result);
        }

        File file = new File("D://User.class");

        FileOutputStream fout = new FileOutputStream(file);
        fout.write(classBytes);
        fout.close();


    }

}
