package com.smarthome;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PriorityQueueTest {

    private PriorityQueue<String> priorityQueue;

    @Before
    public void setUp() {
        priorityQueue = new PriorityQueue<>();
    }

    @Test
    public void testEnqueueAndPeek() {
        priorityQueue.enqueue(new Task<>("Task C", 3));
        priorityQueue.enqueue(new Task<>("Task A", 1));
        priorityQueue.enqueue(new Task<>("Task B", 2));
        assertEquals("Task A", priorityQueue.peek().getTask());
    }

    @Test
    public void testEnqueueToEmptyQueue() {
        Task<String> task = new Task<>("New Task", 2);
        priorityQueue.enqueue(task);
        assertEquals(1, priorityQueue.size());
        assertEquals(task, priorityQueue.peek());
    }

    @Test
    public void testEnqueueWithHigherPriority() {
        priorityQueue.enqueue(new Task<>("Task B", 2));
        priorityQueue.enqueue(new Task<>("Task A", 1));
        assertEquals("Task A", priorityQueue.peek().getTask());
    }

    @Test
    public void testEnqueueWithLowerPriority() {
        priorityQueue.enqueue(new Task<>("Task A", 1));
        priorityQueue.enqueue(new Task<>("Task B", 2));
        assertEquals("Task A", priorityQueue.peek().getTask());
    }

    @Test
    public void testEnqueueWithSamePriority() {
        priorityQueue.enqueue(new Task<>("Task A", 1));
        priorityQueue.enqueue(new Task<>("Task B", 1));
        assertEquals("Task A", priorityQueue.peek().getTask()); // Order might vary, but peek should work
    }

    @Test
    public void testDequeue() {
        priorityQueue.enqueue(new Task<>("Task C", 3));
        priorityQueue.enqueue(new Task<>("Task A", 1));
        priorityQueue.enqueue(new Task<>("Task B", 2));
        priorityQueue.dequeue();
        assertEquals("Task B", priorityQueue.peek().getTask());
    }

    @Test
    public void testDequeueFromEmptyQueue() {
        priorityQueue.dequeue();
        assertTrue(priorityQueue.isEmpty());
    }

    @Test
    public void testPeek() {
        priorityQueue.enqueue(new Task<>("Task B", 2));
        priorityQueue.enqueue(new Task<>("Task A", 1));
        assertEquals("Task A", priorityQueue.peek().getTask());
        assertEquals(2, priorityQueue.size()); // Check that peek doesn't remove
    }

    @Test
    public void testPeekOnEmptyQueue() {
        assertNull(priorityQueue.peek());
    }

    @Test
    public void testSize() {
        priorityQueue.enqueue(new Task<>("Task C", 3));
        priorityQueue.enqueue(new Task<>("Task A", 1));
        assertEquals(2, priorityQueue.size());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(priorityQueue.isEmpty());
        priorityQueue.enqueue(new Task<>("Task A", 1));
        assertFalse(priorityQueue.isEmpty());
    }

    @Test
    public void testClear() {
        priorityQueue.enqueue(new Task<>("Task C", 3));
        priorityQueue.enqueue(new Task<>("Task A", 1));
        priorityQueue.clear();
        assertTrue(priorityQueue.isEmpty());
    }

    @Test
    public void testContains() {
        Task<String> taskA = new Task<>("Task A", 1);
        priorityQueue.enqueue(taskA);
        priorityQueue.enqueue(new Task<>("Task B", 2));
        assertTrue(priorityQueue.contains(taskA));
        assertFalse(priorityQueue.contains(new Task<>("Task C", 3)));
    }

    @Test
    public void testUpdatePriority() {
        Task<String> taskA = new Task<>("Task A", 3);
        priorityQueue.enqueue(taskA);
        priorityQueue.enqueue(new Task<>("Task B", 2));
        priorityQueue.updatePriority(taskA, 1);
        assertEquals("Task A", priorityQueue.peek().getTask());
    }

    @Test
    public void testUpdatePriorityNonExistingTask() {
        Task<String> taskA = new Task<>("Task A", 3);
        priorityQueue.enqueue(new Task<>("Task B", 2));
        priorityQueue.updatePriority(taskA, 1);
        assertEquals("Task B", priorityQueue.peek().getTask());
    }

    @Test
    public void testGetPriority() {
        priorityQueue.enqueue(new Task<>("Task A", 1));
        priorityQueue.enqueue(new Task<>("Task B", 2));
        assertEquals(1, priorityQueue.getPriority("Task A"));
        assertEquals(2, priorityQueue.getPriority("Task B"));
        assertEquals(-1, priorityQueue.getPriority("Task C"));
    }

    @Test
    public void testGetTask() {
        priorityQueue.enqueue(new Task<>("Task A", 1));
        Task<String> taskB = new Task<>("Task B", 2);
        priorityQueue.enqueue(taskB);
        assertEquals("Task A", priorityQueue.getTask(1).getTask());
        assertEquals("Task B", priorityQueue.getTask(2).getTask());
        assertNull(priorityQueue.getTask(3));
    }

    @Test
    public void testGetQueue() {
        Task<String> taskA = new Task<>("Task A", 1);
        Task<String> taskB = new Task<>("Task B", 2);
        priorityQueue.enqueue(taskA);
        priorityQueue.enqueue(taskB);
        ArrayList<Task<String>> queueList = priorityQueue.getQueue();
        assertEquals(2, queueList.size());
        assertEquals(taskA, queueList.get(0));
        assertEquals(taskB, queueList.get(1));
    }
}