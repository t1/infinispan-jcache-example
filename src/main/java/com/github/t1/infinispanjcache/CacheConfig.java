package com.github.t1.infinispanjcache;

import javax.cache.configuration.*;
import javax.cache.expiry.ExpiryPolicy;
import javax.cache.integration.*;

public class CacheConfig<K, V> {
    private final String cacheName;
    private final MutableConfiguration<K, V> config = new MutableConfiguration<>();

    public CacheConfig(String cacheName) {
        this.cacheName = cacheName;
    }

    public String getCacheName() {
        return cacheName;
    }

    public CompleteConfiguration<K, V> getConfig() {
        return config;
    }

    /* ----------------------- delegate methods ----------------------- */
    public MutableConfiguration<K, V> setTypes(Class<K> keyType, Class<V> valueType) {
        return config.setTypes(keyType, valueType);
    }

    public MutableConfiguration<K, V> addCacheEntryListenerConfiguration(
            CacheEntryListenerConfiguration<K, V> cacheEntryListenerConfiguration) {
        return config.addCacheEntryListenerConfiguration(cacheEntryListenerConfiguration);
    }

    public MutableConfiguration<K, V> removeCacheEntryListenerConfiguration(
            CacheEntryListenerConfiguration<K, V> cacheEntryListenerConfiguration) {
        return config.removeCacheEntryListenerConfiguration(cacheEntryListenerConfiguration);
    }

    public MutableConfiguration<K, V> setCacheLoaderFactory(Factory<? extends CacheLoader<K, V>> factory) {
        return config.setCacheLoaderFactory(factory);
    }

    public MutableConfiguration<K, V> setCacheWriterFactory(Factory<? extends CacheWriter<? super K, ? super V>> factory) {
        return config.setCacheWriterFactory(factory);
    }

    public MutableConfiguration<K, V> setExpiryPolicyFactory(Factory<? extends ExpiryPolicy> factory) {
        return config.setExpiryPolicyFactory(factory);
    }

    public MutableConfiguration<K, V> setReadThrough(boolean isReadThrough) {
        return config.setReadThrough(isReadThrough);
    }

    public MutableConfiguration<K, V> setWriteThrough(boolean isWriteThrough) {
        return config.setWriteThrough(isWriteThrough);
    }

    public MutableConfiguration<K, V> setStoreByValue(boolean isStoreByValue) {
        return config.setStoreByValue(isStoreByValue);
    }

    public MutableConfiguration<K, V> setStatisticsEnabled(boolean enabled) {
        return config.setStatisticsEnabled(enabled);
    }

    public MutableConfiguration<K, V> setManagementEnabled(boolean enabled) {
        return config.setManagementEnabled(enabled);
    }
}
