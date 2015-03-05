package com.github.t1.infinispanjcache;

import static java.util.concurrent.TimeUnit.*;

import java.io.Serializable;

import javax.cache.configuration.*;
import javax.cache.event.*;
import javax.cache.expiry.*;

public class MyCacheConfig extends CacheConfig<Object, Object> {
    private static final long serialVersionUID = 1L;

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

    public MyCacheConfig() {
        super("my-cache");
        setExpiryPolicyFactory(ModifiedExpiryPolicy.factoryOf(new Duration(MILLISECONDS, 1000)));
        setStatisticsEnabled(true);
        addCacheEntryListenerConfiguration(listenerConfig());
    }

    private MutableCacheEntryListenerConfiguration<Object, Object> listenerConfig() {
        return new MutableCacheEntryListenerConfiguration<>(
                FactoryBuilder.factoryOf(new SerializableCacheEntryListener<>()),
                FactoryBuilder.factoryOf(new NullCacheEntryEventFilter<>()), false, false);
    }
}
