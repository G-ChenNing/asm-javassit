package com.example.test.cache;

import java.lang.ref.ReferenceQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Musk
 */
public class AbstractWcnCache<K, V> implements WcnCache<K, V> {
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private ConcurrentHashMap<K, WcnCacheReference<V>> cache = new ConcurrentHashMap<K, WcnCacheReference<V>>();
    private ReferenceQueue<V> refQueue = new ReferenceQueue<V>();


    @Override
    public V get(K key) {
        V value = null;

        if (cache.containsKey(key)) {
            WcnCacheReference<V> refValue = cache.get(key);
            value = refValue.get();
        }
        // 如果软引用被回收
        if (value == null) {
            // 清除软引用队列
            clearRefQueue();
            // 创建值并放入缓存 测试方法，注释
//            value = createValue(key);
//            WcnCacheReference<V> refValue = new WcnCacheReference<V>(key, value, refQueue);
//            cache.put(key, refValue);
        }

        return value;
    }

    /**
     * 从软引用队列中移除无效引用，
     * 同时从cache中删除无效缓存
     */
    @SuppressWarnings("unchecked")
    protected void clearRefQueue() {
        WcnCacheReference<V> refValue = null;
        while((refValue = (WcnCacheReference<V>) refQueue.poll()) != null) {
            K key = (K) refValue.getExtraInfo();
            cache.remove(key);
        }
    }


    @Override
    public boolean set(K key, V value) {
        WcnCacheReference<V> refValue = new WcnCacheReference<V>(key, value, refQueue);
        cache.put(key, refValue);
        return true;
    }

    public ConcurrentHashMap<K, WcnCacheReference<V>> getCache() {
        return cache;
    }
}
