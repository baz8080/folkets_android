package com.mbcdev.folkets;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Simple LruCache
 *
 * Created by barry on 12/02/2017.
 */
class LruCache<K, V> extends LinkedHashMap<K, V> {
    private int cacheSize;

    LruCache(int cacheSize) {
        super(16, 0.75f, true);
        this.cacheSize = cacheSize;
    }

    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() >= cacheSize;
    }
}
