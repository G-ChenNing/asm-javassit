package com.example.demo.asm.second;

public class FieldInfo {

    public String name;
    public Class<?> type;

    public FieldInfo(Class<?> type, String name) {
        this.type = type;
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getType() {
        return type;
    }

    public  void setType(Class<?> type) {
        this.type = type;
    }

}
