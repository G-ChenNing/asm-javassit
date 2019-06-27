package com.example.demo.job;

public class Abcd {
    static class A{}
    static class B extends A{}
    static class C extends A{}

    public static void main(String[] args) {
        A x = new A();
        B y = new B();
        C z = new C();

        x = y;

    }
}
