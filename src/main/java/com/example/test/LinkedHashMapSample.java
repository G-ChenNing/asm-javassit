package com.example.test;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LinkedHashMapSample {
    public static void main(String[] args) {
        LinkedHashMap<String, String> accessOrderedMap = new LinkedHashMap<>(16, 0.75F, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                return size() > 3;
            }

            //            protected boolean removeE1desEntry(Map.Entry<String, String> eldes) {
//                // 实现自定义删除策略，否则行为就和普遍Map没有区别
//                return size() > 3;
//            }
        };
        accessOrderedMap.put("Project1", "Valhalla");
        accessOrderedMap.put("Project2", "Panama");
        accessOrderedMap.put("Project3", "Loom");
        accessOrderedMap.forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });
        // 模拟访问
        accessOrderedMap.get("Project2");
        accessOrderedMap.get("Project2");
        accessOrderedMap.get("Project3");
        System.out.println("Iterate over should be not afected:");
        accessOrderedMap.forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });
        // 触发删除
        accessOrderedMap.put("Project4", "Mission Control");
        accessOrderedMap.put("Project5", "w Control");
        accessOrderedMap.put("Project6", "c Control");
        System.out.println("Oldes entry should be removed:");
        accessOrderedMap.forEach((k, v) -> {
            // 遍历顺序不变
            System.out.println(k + ":" + v);
        });
    }
}
