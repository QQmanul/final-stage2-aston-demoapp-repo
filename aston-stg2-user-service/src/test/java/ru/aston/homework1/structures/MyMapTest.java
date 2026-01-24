package ru.aston.homework1.structures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyMapTest {
    private MyMap<String, Integer> map;

    @BeforeEach
    void setUp() {
        map = new MyHashMap<>();
    }

    @Test
    void when_putNewKey_then_sizeIncreases() {
        map.put("a", 1);
        map.put("b", 2);

        assertEquals(2, map.size());
    }

    @Test
    void when_putExistingKey_then_valueIsOverwritten() {
        map.put("x", 10);
        map.put("x", 20);

        assertEquals(1, map.size());
        assertEquals(20, map.get("x"));
    }

    @Test
    void when_getExistingKey_then_returnValue() {
        map.put("id", 100);

        Integer result = map.get("id");

        assertEquals(100, result);
    }

    @Test
    void when_getMissingKey_then_returnNull() {
        map.put("a", 1);

        Integer result = map.get("missing");

        assertNull(result);
    }

    @Test
    void when_removeExistingKey_then_returnTrueAndSizeDecreases() {
        map.put("k1", 5);

        boolean removed = map.remove("k1");

        assertTrue(removed);
        assertEquals(0, map.size());
        assertNull(map.get("k1"));
    }

    @Test
    void when_removeMissingKey_then_returnFalse() {
        map.put("hello", 1);

        boolean removed = map.remove("nope");

        assertFalse(removed);
        assertEquals(1, map.size());
    }

    @Test
    void when_putMultipleKeys_then_theyAreAllAccessible() {
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        assertEquals(3, map.size());
        assertEquals(1, map.get("one"));
        assertEquals(2, map.get("two"));
        assertEquals(3, map.get("three"));
    }

    @Test
    void when_updateDifferentKeys_then_otherValuesRemainUnchanged() {
        map.put("a", 100);
        map.put("b", 200);
        map.put("c", 300);

        map.put("b", 777);

        assertEquals(100, map.get("a"));
        assertEquals(777, map.get("b"));
        assertEquals(300, map.get("c"));
        assertEquals(3, map.size());
    }
}