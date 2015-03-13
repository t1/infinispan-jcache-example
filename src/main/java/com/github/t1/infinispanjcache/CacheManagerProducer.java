package com.github.t1.infinispanjcache;

import javax.annotation.Resource;
import javax.enterprise.inject.Produces;

import org.infinispan.manager.EmbeddedCacheManager;

public class CacheManagerProducer {
    @Produces
    @Resource(lookup = "java:jboss/infinispan/container/app-cache-manager")
    private EmbeddedCacheManager cacheManager;
}
