package com.smarthome;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;

public class LinkedListTest {

    private LinkedList<Integer> list;

    @Before
    public void setUp() {
        list = new LinkedList<>();
    }

    @Test
    public void testAddFront() {
        list.addFront(1);
        list.addFront(2);
        assertEquals(2, (int) list.peek());
    }

    @Test
    public void testAddEnd() {
        list.addEnd(1);
        list.addEnd(2);
        assertEquals(2, (int) list.peekEnd());
    }

    @Test
    public void testAddIndex() {
        list.addEnd(1);
        list.addEnd(3);
        list.addIndex(2, 1);
        assertEquals(2, (int) list.get(1));
    }

    @Test
    public void testPeek() {
        list.addEnd(1);
        assertEquals(1, (int) list.peek());
    }

    @Test
    public void testPeekEnd() {
        list.addEnd(1);
        list.addEnd(2);
        assertEquals(2, (int) list.peekEnd());
    }

    @Test
    public void testRemoveFront() {
        list.addEnd(1);
        list.addEnd(2);
        list.removeFront();
        assertEquals(2, (int) list.peek());
    }

    @Test
    public void testRemoveEnd() {
        list.addEnd(1);
        list.addEnd(2);
        list.removeEnd();
        assertEquals(1, (int) list.peekEnd());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(list.isEmpty());
        list.addEnd(1);
        assertFalse(list.isEmpty());
    }

    @Test
    public void testMakeArrayList() {
        list.addEnd(1);
        list.addEnd(2);
        ArrayList<Integer> arrayList = list.makeArrayList();
        assertEquals(Arrays.asList(1, 2), arrayList);
    }

    @Test
    public void testClear() {
        list.addEnd(1);
        list.addEnd(2);
        list.clear();
        assertTrue(list.isEmpty());
    }

    @Test
    public void testContains() {
        list.addEnd(1);
        list.addEnd(2);
        assertTrue(list.contains(1));
        assertFalse(list.contains(3));
    }

    @Test
    public void testIndexOf() {
        list.addEnd(1);
        list.addEnd(2);
        assertEquals(0, list.indexOf(1));
        assertEquals(-1, list.indexOf(3));
    }

    @Test
    public void testGet() {
        list.addEnd(1);
        list.addEnd(2);
        assertEquals(1, (int) list.get(0));
        assertEquals(2, (int) list.get(1));
    }

    @Test
    public void testRemoveIndex() {
        list.addEnd(1);
        list.addEnd(2);
        list.removeIndex(0);
        assertEquals(2, (int) list.peek());
    }

    @Test
    public void testReverse() {
        list.addEnd(1);
        list.addEnd(2);
        list.reverse();
        assertEquals(2, (int) list.peek());
    }

    @Test
    public void testSort() {
        list.addEnd(2);
        list.addEnd(1);
        list.sort();
        assertEquals(1, (int) list.peek());
    }
}