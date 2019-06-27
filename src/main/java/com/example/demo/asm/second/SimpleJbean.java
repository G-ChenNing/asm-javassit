package com.example.demo.asm.second;


import java.util.List;

public abstract class SimpleJbean {
    public abstract byte[] createBeanClass(String className, List<FieldInfo> fields);
}
