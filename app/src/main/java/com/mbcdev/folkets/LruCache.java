package com.mbcdev.folkets;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Simple LruCache, based on access order
 *
 * Created by barry on 12/02/2017.
 */
class LruCache<K, V> extends LinkedHashMap<K, V> {
    private static final int DEFAULT_MAXIMUM_SIZE = 10;

    private int maximumSize;

    /**
     * Creates an LruCache with the specified maximum size
     *
     * @param maximumSize the maximum size of the cache
     */
    LruCache(int maximumSize) {
        super(maximumSize <= 0 ? DEFAULT_MAXIMUM_SIZE : maximumSize, 0.75f, true);
        this.maximumSize = maximumSize <= 0 ? DEFAULT_MAXIMUM_SIZE : maximumSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maximumSize;
    }

    /**
     * Gets the maximum size allowed in the cache
     *
     * @return the maximum size allowed in the cache
     */
    int getMaximumSize() {
        return maximumSize;
    }
}
