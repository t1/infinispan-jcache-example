package com.github.t1.infinispanjcache;

import javax.annotation.PostConstruct;
import javax.cache.CacheManager;
import javax.ejb.*;
import javax.enterprise.inject.*;
import javax.inject.Inject;

@Startup
@Singleton
public class CacheConfigurationLoader {
    @Inject
    CacheManager cacheManager;

    @Inject
    @Any
    Instance<CacheConfiguration<?, ?>> configs;

    @PostConstruct
    void configure() {
        for (CacheConfiguration<?, ?> config : configs) {
            cacheManager.createCache(config.getCacheName(), config.getConfiguration());
        }
    }
}
