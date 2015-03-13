# JCache Example using Infinispan

Example using the `CacheResult` annotation from [JCache](https://jcp.org/en/jsr/detail?id=107) bound to the implementation from [Infinispan](http://infinispan.org) and _configure it_.

This runs on JBoss 7 with Java 7.

Took me a while to it figure out. Now that it works, I leave it here as an example and starting point.

## Configure Cache

Create cache manager:

```
/subsystem=infinispan/cache-container=app-cache-manager:remove

/subsystem=infinispan/cache-container=app-cache-manager:add(start=EAGER)
```

Create local cache:

```
/subsystem=infinispan/cache-container=app-cache-manager/local-cache=my-cache:add
/subsystem=infinispan/cache-container=app-cache-manager/local-cache=my-cache/expiration=EXPIRATION:add(lifespan=5000)
```

Create clustered cache:

```
/subsystem=infinispan/cache-container=app-cache-manager/transport=TRANSPORT:add(stack=tcp,lock-timeout=30000)
/subsystem=infinispan/cache-container=app-cache-manager/replicated-cache=my-cache:add(mode=ASYNC)
/subsystem=infinispan/cache-container=app-cache-manager/replicated-cache=my-cache/expiration=EXPIRATION:add(lifespan=5000)
```

Info:

```
/subsystem=infinispan/cache-container=app-cache-manager:read-resource(recursive=true)

:reload
```
