package com.github.t1.infinispanjcache;

import static java.util.concurrent.TimeUnit.*;
import static org.junit.Assert.*;

import javax.cache.*;
import javax.cache.configuration.CompleteConfiguration;
import javax.cache.expiry.*;
import javax.inject.Inject;

import org.infinispan.cdi.AdvancedCacheProducer;
import org.infinispan.jcache.annotation.*;
import org.jglue.cdiunit.*;
import org.junit.*;
import org.junit.runner.RunWith;

import com.github.t1.log.LoggingInterceptor;

@RunWith(CdiRunner.class)
@AdditionalClasses({ CacheResultInterceptor.class, DefaultCacheResolver.class, CacheKeyInvocationContextFactory.class,
        CacheConfigLoader.class, CacheManagerProducer.class, LoggingInterceptor.class, MyCacheConfig.class })
@AdditionalPackages({ AdvancedCacheProducer.class })
public class CdiTest {
    @Inject
    CacheConfigLoader config;

    @Inject
    TestResource resource;

    @Inject
    CacheManager cacheManager;

    @After
    public void reset() {
        resource.reset();
    }

    @Test
    public void shouldBeConfigured() {
        @SuppressWarnings("resource")
        Cache<Object, Object> cache = cacheManager.getCache("my-cache");
        @SuppressWarnings("unchecked")
        CompleteConfiguration<Object, Object> config = cache.getConfiguration(CompleteConfiguration.class);
        ExpiryPolicy expiryPolicy = config.getExpiryPolicyFactory().create();

        assertEquals(new Duration(MILLISECONDS, 1000), expiryPolicy.getExpiryForCreation());
        assertEquals(null, expiryPolicy.getExpiryForAccess());
        assertEquals(new Duration(MILLISECONDS, 1000), expiryPolicy.getExpiryForUpdate());
    }

    @Test
    @Ignore("requires @Interceptors(CacheResultInterceptor.class) annotation on TestResource#get()")
    public void shouldCache() throws Exception {
        assertEquals("### 0\n", get());
        assertEquals("### 0\n", get());

        Thread.sleep(1200);

        assertEquals("### 1\n", get());
    }

    private String get() {
        String result = resource.get("###");
        System.out.println("-> " + result);
        return result;
    }
}
