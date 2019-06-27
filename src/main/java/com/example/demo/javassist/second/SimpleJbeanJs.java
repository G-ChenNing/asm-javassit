package com.example.demo.javassist.second;

import java.util.List;


import com.example.demo.asm.second.FieldInfo;
import com.example.demo.asm.second.SimpleJbean;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import org.apache.commons.lang3.StringUtils;

/**
 * JbeanUtil.java. 2011-12-26下午4:05:02 @author LionBule.
 */
public class SimpleJbeanJs extends SimpleJbean {
    private final static String SETTER_STR    = "set";
    private final static String GETTER_STR    = "get";
    // type/fieldName
    private final static String fieldTemplate = "private %s %s;";

    @Override
    public byte[] createBeanClass(String className, List<FieldInfo> fields){
        try{
            ClassPool cpool = ClassPool.getDefault();
            CtClass cc = cpool.makeClass(StringUtils.capitalize(className));

            String setMethodName = null;
            String getMethodName = null;

            for (FieldInfo fi : fields) {
                setMethodName = SETTER_STR + StringUtils.capitalize(fi.name);
                getMethodName = GETTER_STR + StringUtils.capitalize(fi.name);

                CtField newField = CtField.make(String.format(fieldTemplate, fi.type.getName(), fi.name), cc);
                cc.addField(newField);

                CtMethod ageSetter = CtNewMethod.setter(setMethodName, newField);
                cc.addMethod(ageSetter);
                CtMethod ageGetter = CtNewMethod.getter(getMethodName, newField);
                cc.addMethod(ageGetter);
            }

            return cc.toBytecode();
        }catch(Exception e){
            throw new RuntimeException(e);
        }

    }

}