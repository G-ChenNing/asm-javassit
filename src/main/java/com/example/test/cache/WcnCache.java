package com.example.test.cache;

public interface WcnCache<K, V> {
    public V get(K key);
    public boolean set(K key, V value);
}
