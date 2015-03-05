package com.github.t1.infinispanjcache;

import javax.annotation.PostConstruct;
import javax.cache.CacheManager;
import javax.ejb.*;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@Startup
@Singleton
public class CacheConfigLoader {
    @Inject
    CacheManager cacheManager;

    @Inject
    Instance<CacheConfig<?, ?>> configs;

    @PostConstruct
    void configure() {
        for (CacheConfig<?, ?> config : configs) {
            cacheManager.createCache(config.getCacheName(), config.getConfiguration());
        }
    }
}
