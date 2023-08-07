package com.sindercube.iconic.cache;

import java.util.*;

/**
 * @param <T> The key to identify your value by
 * @param <V> The value you want to cache
 */
public class MappedCache<T, V> extends Cache {

    public LinkedHashMap<T, V> map = new LinkedHashMap<>();
    private final int max_cache_size;
    public MappedCache() { this(1); }
    public MappedCache(Integer maxCacheSize) {
        super();
        if (maxCacheSize < 1) maxCacheSize = 1;
        this.max_cache_size = maxCacheSize;
        CacheReloader.CACHES.add(this);
    }
    public V get(T key) {
        return this.map.getOrDefault(key, null);
    }
    public V put(T key, V value) {
        this.map.put(key, value);
        while (this.map.keySet().size() > this.max_cache_size) this.map.keySet().stream().findFirst().ifPresent(this.map::remove);
        return value;
    }
    public boolean has(T key) {
        return this.map.containsKey(key);
    }
    @Override
    public void clear() {
        this.map = new LinkedHashMap<>();
    }
}
