package com.github.t1.infinispanjcache;

import static com.github.t1.log.LogLevel.*;
import static javax.ws.rs.core.MediaType.*;

import javax.cache.annotation.CacheResult;
import javax.ws.rs.*;

import com.github.t1.log.Logged;

@Path("")
@Logged(level = INFO)
public class TestResource {
    private static int nextId = 0;

    @GET
    @Path("{prefix}")
    @Produces(TEXT_PLAIN)
    @CacheResult(cacheName = "my-cache")
    // this is required for the CdiUnit-Tests @Interceptors(CacheResultInterceptor.class)
    public String get(@PathParam("prefix") String prefix) {
        String message = prefix + " " + nextId++;
        System.out.println(message);
        return message + "\n";
    }

    public void reset() {
        nextId = 0;
    }
}
