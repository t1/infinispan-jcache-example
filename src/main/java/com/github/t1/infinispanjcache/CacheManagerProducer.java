package com.github.t1.infinispanjcache;

import javax.cache.*;
import javax.cache.spi.CachingProvider;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.*;

public class CacheManagerProducer {
    private final CachingProvider provider = Caching.getCachingProvider();

    @Produces
    @ApplicationScoped
    CacheManager produce() {
        return provider.getCacheManager(provider.getDefaultURI(), provider.getDefaultClassLoader());
    }

    void dispose(@Disposes CacheManager cacheManager) {
        cacheManager.close();
    }
}
