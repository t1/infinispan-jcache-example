package com.github.t1.infinispanjcache;

import static org.junit.Assert.*;

import java.util.*;

import javax.enterprise.inject.*;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.*;

import org.jglue.cdiunit.CdiRunner;
import org.junit.*;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
public class GreetingsCollector {
    static class Greeting {
        public final String text;

        public Greeting(String text) {
            this.text = text;
        }
    }

    @Produces
    @Named("hi")
    Greeting hi = new Greeting("Hi");

    @Produces
    @Named("hello")
    Greeting hello = new Greeting("Hello");

    @Inject
    Instance<Greeting> greetings;


    @Test
    public void shouldCollectAll() {
        Set<String> set = new HashSet<>();
        for (Greeting greeting : greetings) {
            set.add(greeting.text);
        }
        assertEquals(2, set.size());
        assertTrue(set.contains("Hi"));
        assertTrue(set.contains("Hello"));
    }

    @SuppressWarnings("all")
    private static class NamedLiteral extends AnnotationLiteral<Named> implements Named {
        private final String name;

        public NamedLiteral(String name) {
            this.name = name;
        }

        @Override
        public String value() {
            return name;
        }
    }

    @Test
    public void shouldCollectNamedHi() {
        Greeting greeting = greetings.select(new NamedLiteral("hi")).get();
        assertEquals("Hi", greeting.text);
    }

    @Test
    @Ignore
    public void shouldCollectAllWithMeta() {
        Map<String, Greeting> map = new HashMap<>();
        for (Greeting greeting : greetings) {
            Annotated annotated = magic(greeting);
            String name = annotated.getAnnotation(Named.class).value();
            map.put(name, greeting);
        }
        assertEquals(2, map.size());
        assertEquals("Hi", map.get("hi"));
        assertEquals("Hello", map.get("hello"));
    }

    private Annotated magic(Greeting greeting) {
        return null; // TODO how?
    }
}
