package com.github.t1.infinispanjcache;

import static java.util.concurrent.TimeUnit.*;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.cache.*;
import javax.cache.configuration.*;
import javax.cache.event.*;
import javax.cache.expiry.*;
import javax.ejb.*;
import javax.inject.Inject;

@Startup
@Singleton
public class CacheConfig {
    private static class SerializableCacheEntryListener<K, V> implements CacheEntryExpiredListener<K, V>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public void onExpired(Iterable<CacheEntryEvent<? extends K, ? extends V>> events)
                throws CacheEntryListenerException {
            for (CacheEntryEvent<? extends K, ? extends V> event : events) {
                System.out.println("expired value [" + event.getValue() + "] from " + event.getSource().getName());
            }
        }
    }

    private static class NullCacheEntryEventFilter<K, V> implements CacheEntryEventFilter<K, V>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public boolean evaluate(CacheEntryEvent<? extends K, ? extends V> event) throws CacheEntryListenerException {
            return true;
        }
    }

    @Inject
    CacheManager cacheManager;

    @PostConstruct
    void configure() {
        MutableConfiguration<Object, Object> config = config("my-cache");
        boolean isNew = (config == null);
        if (config == null)
            config = new MutableConfiguration<>();
        config.setExpiryPolicyFactory(ModifiedExpiryPolicy.factoryOf(new Duration(MILLISECONDS, 1000)));
        config.setStatisticsEnabled(true);
        config.addCacheEntryListenerConfiguration(listenerConfig());
        if (isNew)
            cacheManager.createCache("my-cache", config);
    }

    @SuppressWarnings({ "resource", "unchecked" })
    private MutableConfiguration<Object, Object> config(String cacheName) {
        Cache<Object, Object> cache = cacheManager.getCache(cacheName);
        if (cache != null)
            return cache.getConfiguration(MutableConfiguration.class);
        return null;
    }

    private MutableCacheEntryListenerConfiguration<Object, Object> listenerConfig() {
        return new MutableCacheEntryListenerConfiguration<>(
                FactoryBuilder.factoryOf(new SerializableCacheEntryListener<>()),
                FactoryBuilder.factoryOf(new NullCacheEntryEventFilter<>()), false, false);
    }
}
