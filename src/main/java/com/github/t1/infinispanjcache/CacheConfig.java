package com.github.t1.infinispanjcache;

import java.util.concurrent.TimeUnit;

import javax.cache.configuration.*;
import javax.cache.event.CacheEntryListener;
import javax.cache.expiry.*;
import javax.cache.integration.*;

public class CacheConfig<K, V> {
    public static Duration duration(int durationAmount, TimeUnit timeUnit) {
        return new Duration(timeUnit, durationAmount);
    }

    private final String cacheName;
    private final MutableConfiguration<K, V> configuration = new MutableConfiguration<>();

    public CacheConfig(String cacheName) {
        this.cacheName = cacheName;
    }

    public String getCacheName() {
        return cacheName;
    }

    public CompleteConfiguration<K, V> getConfiguration() {
        return configuration;
    }

    public CacheConfig<K, V> expireWhenNotAccessedFor(int durationAmount, TimeUnit timeUnit) {
        return setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(duration(durationAmount, timeUnit)));
    }

    public CacheConfig<K, V> expireWhenNotCreatedFor(int durationAmount, TimeUnit timeUnit) {
        return setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(duration(durationAmount, timeUnit)));
    }

    public CacheConfig<K, V> neverExpire() {
        return setExpiryPolicyFactory(EternalExpiryPolicy.factoryOf());
    }

    public CacheConfig<K, V> expireWhenNotModifiedFor(int durationAmount, TimeUnit timeUnit) {
        return setExpiryPolicyFactory(ModifiedExpiryPolicy.factoryOf(duration(durationAmount, timeUnit)));
    }

    public CacheConfig<K, V> expireWhenNotTouchedFor(int durationAmount, TimeUnit timeUnit) {
        return setExpiryPolicyFactory(TouchedExpiryPolicy.factoryOf(duration(durationAmount, timeUnit)));
    }

    public CacheConfig<K, V> addCacheEntryListener(Factory<? extends CacheEntryListener<K, V>> listenerFactory) {
        configuration.addCacheEntryListenerConfiguration(new MutableCacheEntryListenerConfiguration<>(//
                listenerFactory, null, false, false));
        return this;
    }

    /* ----------------------------------- delegate methods ----------------------------------- */

    public CacheConfig<K, V> setTypes(Class<K> keyType, Class<V> valueType) {
        configuration.setTypes(keyType, valueType);
        return this;
    }

    public CacheConfig<K, V> setCacheLoaderFactory(Factory<? extends CacheLoader<K, V>> factory) {
        configuration.setCacheLoaderFactory(factory);
        return this;
    }

    public CacheConfig<K, V> setCacheWriterFactory(Factory<? extends CacheWriter<? super K, ? super V>> factory) {
        configuration.setCacheWriterFactory(factory);
        return this;
    }

    public CacheConfig<K, V> setExpiryPolicyFactory(Factory<? extends ExpiryPolicy> factory) {
        configuration.setExpiryPolicyFactory(factory);
        return this;
    }

    public CacheConfig<K, V> setReadThrough(boolean isReadThrough) {
        configuration.setReadThrough(isReadThrough);
        return this;
    }

    public CacheConfig<K, V> setWriteThrough(boolean isWriteThrough) {
        configuration.setWriteThrough(isWriteThrough);
        return this;
    }

    public CacheConfig<K, V> setStoreByValue(boolean isStoreByValue) {
        configuration.setStoreByValue(isStoreByValue);
        return this;
    }

    public CacheConfig<K, V> setStatisticsEnabled(boolean enabled) {
        configuration.setStatisticsEnabled(enabled);
        return this;
    }

    public CacheConfig<K, V> setManagementEnabled(boolean enabled) {
        configuration.setManagementEnabled(enabled);
        return this;
    }
}
