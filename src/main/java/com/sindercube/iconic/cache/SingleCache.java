package com.sindercube.iconic.cache;

public class SingleCache<T> extends Cache {
    public T value;
    public T defaultValue;
    public SingleCache() {
        this(null);
    }
    public SingleCache(T value) {
        this.value = value;
        this.defaultValue = value;
        CacheReloader.CACHES.add(this);
    }
    public boolean isEmpty() {
        return this.value == null;
    }
    @Override
    public void clear() {
        this.value = this.defaultValue;
    }
}
