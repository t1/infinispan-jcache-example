package com.github.t1.infinispanjcache;

import static javax.ws.rs.core.MediaType.*;

import javax.cache.annotation.CacheResult;
import javax.ws.rs.*;

@Path("")
public class TestResource {
    private static int nextId = 0;

    @GET
    @Path("{prefix}")
    @Produces(TEXT_PLAIN)
    @CacheResult(cacheName = "my-cache")
    public String get(@PathParam("prefix") String prefix) {
        String message = prefix + " " + nextId++;
        System.out.println(message);
        return message + "\n";
    }

    public void reset() {
        nextId = 0;
    }
}
