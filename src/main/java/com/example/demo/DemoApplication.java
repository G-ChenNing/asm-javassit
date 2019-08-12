package com.example.demo;

import java.lang.ref.*;
import java.util.concurrent.ConcurrentHashMap;

//@SpringBootApplication
public class DemoApplication {
    public static class A{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static void main(String[] args) {
//        SpringApplication.run(DemoApplication.class, args);
        A counter = new A();
        counter.setName("1");
        A counter2 = new A();
        counter2.setName("2");
        ReferenceQueue refQueue = new ReferenceQueue<>();
//        PhantomReference<Object> p = new PhantomReference<Object>(counter, refQueue);
        WeakReference<A> p = new WeakReference<>(counter, refQueue);
        WeakReference<A> p2 = new WeakReference<>(counter, refQueue);

//        SoftReference<Object> p = new SoftReference<>(counter, refQueue);
//        counter = null;
        System.out.println(p.enqueue());
        System.out.println(p2.enqueue());
//        System.gc();
        System.out.println(p.isEnqueued());



        try {  // Remove是一个阻塞方法，可以指定timeout，或者选择一直阻塞
            Reference ref;
            while((ref = refQueue.remove(1000))!=null) {
                if (ref != null) {
                    System.out.println(ref.get());
                    // do something  }
                    System.out.println(p.get());
                    System.out.println("回收了");
                }
//                System.out.println("1");
//                System.out.println("还在");
//                System.out.println(p.get());
//                System.out.println(p.isEnqueued());
            }
        } catch (InterruptedException e) {  // Handle it }
            
        }
        ConcurrentHashMap<String, String> ax = new ConcurrentHashMap<>();
    }
}
