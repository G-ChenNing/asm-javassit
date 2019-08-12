package com.example.test.cache;

import com.example.demo.DemoApplication;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheTest {
    public static void main(String[] args) throws InterruptedException {
        AbstractWcnCache<String, DemoApplication.A> map = new AbstractWcnCache<>();

        DemoApplication.A counter = new DemoApplication.A();
        counter.setName("2");
        map.set("1", counter);
        counter = null;

        System.gc();

        System.out.println(map.get("1"));
        for(Map.Entry<String, WcnCacheReference<DemoApplication.A >> entry: map.getCache().entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue().getExtraInfo());
            System.out.println(map.get((String)entry.getValue().getExtraInfo()));
        }
//        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
//        String a = "1";
//        map.put("1", a);
//        a = null;
//        Thread.sleep(5000);
//        System.gc();
//
//        for(Map.Entry<String, Object> entry: map.entrySet()) {
//            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
//        }
    }
}
