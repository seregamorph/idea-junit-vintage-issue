package com.example;

import org.junit.Test;

import java.util.Collections;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

public class Unit1Test {

    @Test
    public void testStackTrace() {
        Exception exception = new Exception();
        System.out.println("\n=== Full stacktrace ===");
        exception.printStackTrace();
        System.out.println("^^^ Stacktrace ^^^\n");
        boolean hasVintage = Stream.of(exception.getStackTrace())
                .map(Object::toString)
                .anyMatch(str -> str.contains("org.junit.vintage.engine"));
        assertTrue("junit-vintage-engine is expected in the classpath. It is not there, it means " +
                "old JUnitCore (com.intellij.junit4.JUnit4IdeaTestRunner) is used instead of expected " +
                "com.intellij.junit5.JUnit5IdeaTestRunner", hasVintage);
    }

    @Test
    public void testClasspath() throws Exception {
        Class<?> cls = org.junit.platform.engine.EngineDiscoveryRequest.class;
        String classResource = cls.getName().replace('.', '/') + ".class";
        var resources = Collections.list(getClass().getClassLoader().getResources(classResource));
        if (resources.size() != 1) {
            throw new AssertionError("Unexpected resources:\n" + resources);
        }
    }
}
