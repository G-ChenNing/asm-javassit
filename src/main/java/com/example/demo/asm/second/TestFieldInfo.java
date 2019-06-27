package com.example.demo.asm.second;

public class TestFieldInfo extends FieldInfo {
    public Object value;

    public TestFieldInfo(Class<?> type, String name, Object value) {
        super(type, name);
        this.value = value;
    }
}
