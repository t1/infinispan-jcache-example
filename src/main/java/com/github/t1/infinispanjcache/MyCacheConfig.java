package com.github.t1.infinispanjcache;

import static java.util.concurrent.TimeUnit.*;
import static javax.cache.configuration.FactoryBuilder.*;

import java.io.Serializable;

import javax.cache.event.*;
import javax.enterprise.inject.Produces;

import org.slf4j.*;

public class MyCacheConfig {
    private static class MyCacheEntryListener<K, V> implements CacheEntryExpiredListener<K, V>, Serializable {
        private static final long serialVersionUID = 1L;

        private static final Logger log = LoggerFactory.getLogger(MyCacheEntryListener.class);

        @Override
        public void onExpired(Iterable<CacheEntryEvent<? extends K, ? extends V>> events) {
            for (CacheEntryEvent<? extends K, ? extends V> event : events) {
                log.debug("expired value [{}] from {}", event.getValue(), event.getSource().getName());
            }
        }
    }

    @Produces
    public CacheConfig<Object, Object> produceCacheConfig() {
        return new CacheConfig<>("my-cache") //
                .expireWhenNotTouchedFor(1000, MILLISECONDS) //
                .setStatisticsEnabled(true) //
                .addCacheEntryListener(factoryOf(new MyCacheEntryListener<>()));
    }
}
