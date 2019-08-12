package com.example.test.cache;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * @author Musk   高速自回收缓存
 */
public class WcnCacheReference<T> extends WeakReference<T> {
    private Object info;

    public WcnCacheReference(Object info, T t, ReferenceQueue<T> refQueue) {
        super(t, refQueue);
        this.info = info;
    }

    public Object getExtraInfo() {
        return this.info;
    }
}
