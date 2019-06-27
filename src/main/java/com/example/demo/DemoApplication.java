package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.ref.*;
import java.util.HashMap;

//@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
//        SpringApplication.run(DemoApplication.class, args);
        Object counter = new Object();
        ReferenceQueue refQueue = new ReferenceQueue<>();
        PhantomReference<Object> p = new PhantomReference<Object>(counter, refQueue);
//        WeakReference<Object> p = new WeakReference<>(counter, refQueue);
//        SoftReference<Object> p = new SoftReference<>(counter, refQueue);
        counter = null;
        System.gc();
        System.out.println(p.isEnqueued());
        try {  // Remove是一个阻塞方法，可以指定timeout，或者选择一直阻塞
            Reference<Object> ref = refQueue.remove(1000);
            if (ref != null) {
                System.out.println(ref.get());
                // do something  }
                System.out.println("回收了");
                return;
            }
            System.out.println("1");
            System.out.println("还在");
            System.out.println(p.get());
        } catch (InterruptedException e) {  // Handle it }
            
        }

    }
}
