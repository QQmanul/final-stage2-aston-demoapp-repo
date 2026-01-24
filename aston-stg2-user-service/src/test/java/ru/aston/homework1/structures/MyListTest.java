package ru.aston.homework1.structures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyListTest {
    private MyList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new MyLinkedList<>();
    }

    @Test
    void when_addElement_then_listCountIncreases() {
        list.add(10);
        assertEquals(1, list.getListCount());
    }

    @Test
    void when_addAllElements_then_allElementsPresent() {
        Integer[] values = {1, 2, 3};
        list.addAll(values);

        assertEquals(3, list.getListCount());
        assertTrue(list.contains(1));
        assertTrue(list.contains(2));
        assertTrue(list.contains(3));
    }

    @Test
    void when_removeExistingElement_then_returnTrueAndRemoveIt() {
        list.add(5);

        boolean removed = list.remove(5);

        assertTrue(removed);
        assertEquals(0, list.getListCount());
        assertFalse(list.contains(5));
    }

    @Test
    void when_removeNonExistingElement_then_returnFalse() {
        list.add(1);

        boolean removed = list.remove(999);

        assertFalse(removed);
        assertEquals(1, list.getListCount());
    }

    @Test
    void when_containsOnExistingElement_then_returnTrue() {
        list.add(100);

        assertTrue(list.contains(100));
    }

    @Test
    void when_containsOnMissingElement_then_returnFalse() {
        list.add(1);

        assertFalse(list.contains(2));
    }

    @Test
    void when_getExistingItem_then_returnItem() {
        list.add(42);

        Item<Integer> item = list.get(42);

        assertNotNull(item);
        assertEquals(42, item.getValue());
    }

    @Test
    void when_getMissingItem_then_returnNull() {
        list.add(1);

        Item<Integer> item = list.get(999);

        assertNull(item);
    }

    @Test
    void when_addItem_then_firstItemChanges() {
        Item<Integer> item = new Item<>(777);

        list.addItem(item);

        assertEquals(777, list.getFirstItem().getValue());
    }

    @Test
    void when_getFirstItem_then_returnHeadOfList() {
        list.add(1);
        list.add(2);
        list.add(3);

        assertEquals(3, list.getFirstItem().getValue());
    }
}