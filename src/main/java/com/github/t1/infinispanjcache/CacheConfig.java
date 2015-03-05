package com.github.t1.infinispanjcache;

import javax.cache.configuration.MutableConfiguration;

public class CacheConfig<K, V> extends MutableConfiguration<K, V> {
    private static final long serialVersionUID = 1L;

    private final String cacheName;

    public CacheConfig(String cacheName) {
        this.cacheName = cacheName;
    }

    public String getCacheName() {
        return cacheName;
    }
}
