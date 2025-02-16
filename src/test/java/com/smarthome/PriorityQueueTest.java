package com.smarthome;

import com.smarthome.datastuctures.PriorityQueue;
import com.smarthome.tasks.Task;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

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

        @Test
        public void enqueue_emptyQueue() {
            PriorityQueue<String> queue = new PriorityQueue<>();
            Task<String> task = new Task<>("Task1", 1);
            queue.enqueue(task);
            assertEquals(1, queue.size());
            assertEquals(task, queue.peek());
        }

        @Test
        public void enqueue_higherPriority() {
            PriorityQueue<String> queue = new PriorityQueue<>(new Task<>("Task1", 2));
            Task<String> newTask = new Task<>("Task2", 1);
            queue.enqueue(newTask);
            assertEquals(2, queue.size());
            assertEquals(newTask, queue.peek());
        }

        @Test
        public void enqueue_lowerPriority() {
            PriorityQueue<String> queue = new PriorityQueue<>(new Task<>("Task1", 1));
            Task<String> newTask = new Task<>("Task2", 2);
            queue.enqueue(newTask);
            assertEquals(2, queue.size());
            assertEquals("Task1", queue.peek().getTask()); // Check peek, not newTask
        }
        @Test
        public void enqueue_middlePriority() {
            PriorityQueue<String> queue = new PriorityQueue<>();
            queue.enqueue(new Task<>("Task1", 1));
            queue.enqueue(new Task<>("Task3", 3));
            Task<String> newTask = new Task<>("Task2", 2);
            queue.enqueue(newTask);

            assertEquals(3, queue.size());
            ArrayList<Task<String>> taskList = queue.getQueue();

            assertEquals("Task1", taskList.get(0).getTask());
            assertEquals("Task2", taskList.get(1).getTask());
            assertEquals("Task3", taskList.get(2).getTask());
        }

        @Test
        public  void enqueue_samePriority() {
            PriorityQueue<String> queue = new PriorityQueue<>(new Task<>("Task1", 1));
            Task<String> newTask = new Task<>("Task2", 1);
            queue.enqueue(newTask);
            assertEquals(2, queue.size());
            // Order not guaranteed for same priority, so just check for presence
            assertTrue(queue.contains(newTask));
            assertTrue(queue.contains(new Task<>("Task1", 1)));
        }


        @Test
        public  void dequeue_emptyQueue() {
            PriorityQueue<String> queue = new PriorityQueue<>();
            assertNull(queue.dequeue());
        }

        @Test
        public void dequeue_singleElement()  {
            PriorityQueue<String> queue = new PriorityQueue<>(new Task<>("Task1", 1));
            Task<String> task = queue.dequeue();
            assertEquals("Task1", task.getTask());
            assertEquals(0, queue.size());
            assertTrue(queue.isEmpty());
        }

        @Test
        public void dequeue_multipleElements() {
            PriorityQueue<String> queue = new PriorityQueue<>();
            Task<String> task1 = new Task<>("Task1", 1);
            Task<String> task2 = new Task<>("Task2", 2);
            queue.enqueue(task1);
            queue.enqueue(task2);
            assertEquals(task1, queue.dequeue());
            assertEquals(task2, queue.dequeue());
            assertTrue(queue.isEmpty());
        }

        @Test
        public   void dequeue_correctOrder() {
            PriorityQueue<String> queue = new PriorityQueue<>();
            queue.enqueue(new Task<>("Task3", 3));
            queue.enqueue(new Task<>("Task1", 1));
            queue.enqueue(new Task<>("Task2", 2));

            assertEquals("Task1", queue.dequeue().getTask());
            assertEquals("Task2", queue.dequeue().getTask());
            assertEquals("Task3", queue.dequeue().getTask());
            assertTrue(queue.isEmpty());
        }


        @Test
        public void peek_emptyQueue() {
            PriorityQueue<String> queue = new PriorityQueue<>();
            assertNull(queue.peek());
        }

        @Test
        public  void peek_singleElement() {
            Task<String> task = new Task<>("Task1", 1);
            PriorityQueue<String> queue = new PriorityQueue<>(task);
            assertEquals(task, queue.peek());
            assertEquals(1, queue.size()); // Peek shouldn't remove
        }

        @Test
        public   void peek_multipleElements() {
            PriorityQueue<String> queue = new PriorityQueue<>();
            queue.enqueue(new Task<>("Task2", 2));
            queue.enqueue(new Task<>("Task1", 1));
            assertEquals("Task1", queue.peek().getTask());
            assertEquals(2, queue.size());  // Peek shouldn't remove
        }


        @Test
        public  void getQueue_emptyQueue() {
            PriorityQueue<String> queue = new PriorityQueue<>();
            assertTrue(queue.getQueue().isEmpty());
        }

        @Test
        public     void getQueue_multipleElements() {
            PriorityQueue<String> queue = new PriorityQueue<>();
            Task<String> task1 = new Task<>("Task1", 1);
            Task<String> task2 = new Task<>("Task2", 2);
            queue.enqueue(task1);
            queue.enqueue(task2);
            ArrayList<Task<String>> taskList = queue.getQueue();
            assertEquals(2, taskList.size());
            assertEquals(task1, taskList.get(0));
            assertEquals(task2, taskList.get(1));  // Check order preservation
        }
        @Test
        public    void getQueue_orderPreservation() {
            PriorityQueue<String> queue = new PriorityQueue<>();
            queue.enqueue(new Task<>("Task3", 3));
            queue.enqueue(new Task<>("Task1", 1));
            queue.enqueue(new Task<>("Task2", 2));

            ArrayList<Task<String>> taskList = queue.getQueue();
            assertEquals("Task1", taskList.get(0).getTask());
            assertEquals("Task2", taskList.get(1).getTask());
            assertEquals("Task3", taskList.get(2).getTask());
        }


        @Test
        public void size_emptyQueue() {
            PriorityQueue<String> queue = new PriorityQueue<>();
            assertEquals(0, queue.size());
        }

        @Test
        public void size_afterEnqueueDequeue() {
            PriorityQueue<String> queue = new PriorityQueue<>();
            queue.enqueue(new Task<>("Task1", 1));
            queue.enqueue(new Task<>("Task2", 2));
            queue.dequeue();
            assertEquals(1, queue.size());
        }


        @Test
        public void isEmpty_emptyQueue() {
            PriorityQueue<String> queue = new PriorityQueue<>();
            assertTrue(queue.isEmpty());
        }

        @Test
        public  void isEmpty_afterEnqueueDequeue() {
            PriorityQueue<String> queue = new PriorityQueue<>();
            queue.enqueue(new Task<>("Task1", 1));
            queue.dequeue();
            assertTrue(queue.isEmpty());
        }

        @Test
        public void isEmpty_notEmpty() {
            PriorityQueue<String> queue = new PriorityQueue<>(new Task<>("Task1", 1));
            assertFalse(queue.isEmpty());
        }


        @Test
        public void clear_emptyQueue() {
            PriorityQueue<String> queue = new PriorityQueue<>();
            queue.clear();
            assertTrue(queue.isEmpty());
        }
        @Test
        public void clear_nonEmptyQueue() {
            PriorityQueue<String> queue = new PriorityQueue<>();
            queue.enqueue(new Task<>("Task1", 1));
            queue.enqueue(new Task<>("Task2", 2));
            queue.clear();
            assertTrue(queue.isEmpty());
            assertEquals(0, queue.size()); // Also check size
            assertNull(queue.peek());    // And peek
        }


        @Test
        public void contains_emptyQueue() {
            PriorityQueue<String> queue = new PriorityQueue<>();
            assertFalse(queue.contains(new Task<>("Task1", 1)));
        }

        @Test
        public void contains_presentElement() {
            Task<String> task = new Task<>("Task1", 1);
            PriorityQueue<String> queue = new PriorityQueue<>(task);
            assertTrue(queue.contains(task));
        }
        @Test
        public void contains_equalButNotSameTask() {
            PriorityQueue<String> queue = new PriorityQueue<>();
            Task<String> task1 = new Task<>("Task", 1);
            queue.enqueue(task1);
            Task<String> task2 = new Task<>("Task", 1); // Same content, different object
            assertTrue(queue.contains(task2)); // Should still find it based on equality
        }

        @Test
        public void contains_absentElement() {
            PriorityQueue<String> queue = new PriorityQueue<>(new Task<>("Task1", 1));
            assertFalse(queue.contains(new Task<>("Task2", 2)));
        }


        @Test
        public void removeTask_emptyQueue() {
            PriorityQueue<String> queue = new PriorityQueue<>();
            queue.removeTask("Task1");
            assertTrue(queue.isEmpty());
        }

        @Test
        public void removeTask_presentTask() {
            PriorityQueue<String> queue = new PriorityQueue<>();
            queue.enqueue(new Task<>("Task1", 1));
            queue.removeTask("Task1");
            assertTrue(queue.isEmpty());
        }
        @Test
        public void removeTask_nonPresentTask() {
            PriorityQueue<String> queue = new PriorityQueue<>(new Task<>("Task1", 1));
            queue.removeTask("Task2");
            assertEquals(1, queue.size()); // Size should remain unchanged
            assertEquals("Task1", queue.peek().getTask());  // Check the remaining task
        }


        @Test
        public void getTask_byTaskContent_presentTask() {
            PriorityQueue<String> queue = new PriorityQueue<>();
            Task<String> task1 = new Task<>("Task1", 1);
            queue.enqueue(task1);
            assertEquals(task1, queue.getTask("Task1"));
        }

        @Test
        public void getTask_byTaskContent_absentTask() {
            PriorityQueue<String> queue = new PriorityQueue<>(new Task<>("Task1", 1));
            assertNull(queue.getTask("Task2"));
        }

        @Test
        public  void updatePriority_presentTask() {
            PriorityQueue<String> queue = new PriorityQueue<>();
            Task<String> task = new Task<>("Task1", 1);
            queue.enqueue(task);
            queue.updatePriority(task, 2);
            assertEquals(2, queue.getPriority("Task1"));
            assertEquals(1, queue.size());
        }

        @Test
        public  void updatePriority_absentTask() {
            PriorityQueue<String> queue = new PriorityQueue<>(new Task<>("Task1", 1));
            Task<String> task2 = new Task<>("Task2", 2);
            queue.updatePriority(task2, 3); //Updating a non-existent task.
            assertEquals(1, queue.size()); // Size remains unchanged
            assertEquals(1, queue.getPriority("Task1"));  // Existing task is unchanged
            assertEquals(-1, queue.getPriority("Task2")); // Non-existent task remains non-existent
        }

        @Test
        public   void getPriority_presentTask() {
            PriorityQueue<String> queue = new PriorityQueue<>(new Task<>("Task1", 1));
            assertEquals(1, queue.getPriority("Task1"));
        }

        @Test
        public  void getPriority_absentTask() {
            PriorityQueue<String> queue = new PriorityQueue<>(new Task<>("Task1", 1));
            assertEquals(-1, queue.getPriority("Task2"));
        }


        @Test
        public     void getTaskByPriority_presentPriority() {
            PriorityQueue<String> queue = new PriorityQueue<>();
            Task<String> task1 = new Task<>("Task1", 1);
            queue.enqueue(task1);
            assertEquals(task1, queue.getTask(1));
        }

        @Test
        public void getTaskByPriority_absentPriority() {
            PriorityQueue<String> queue = new PriorityQueue<>(new Task<>("Task1", 1));
            assertNull(queue.getTask(2));
        }

        @Test
        public void getTaskByPriority_multipleTasksSamePriority_returnsFirst() {
            PriorityQueue<String> queue = new PriorityQueue<>();
            Task<String> task1 = new Task<>("Task1", 1);
            Task<String> task2 = new Task<>("Task2", 1); // Same priority
            queue.enqueue(task1);
            queue.enqueue(task2);
            assertEquals(task1, queue.getTask(1)); // Should return the first one added
        }

        @Test
        public void testConcurrency_enqueueDequeue() throws InterruptedException {
            final PriorityQueue<String> queue = new PriorityQueue<>();
            final int numThreads = 4;
            final int tasksPerThread = 1000;
            Thread[] threads = new Thread[numThreads];

            // Enqueue threads
            for (int i = 0; i < numThreads; i++) {
                final int threadId = i;
                threads[i] = new Thread(() -> {
                    for (int j = 0; j < tasksPerThread; j++) {
                        queue.enqueue(new Task<>("Task-" + threadId + "-" + j, j % 5)); // Varying priorities
                    }
                });
                threads[i].start();
            }
            for (Thread t : threads) {
                t.join();
            }
            // Dequeue threads.  We'll check consistency *after* all operations.
            Thread[] dequeueThreads = new Thread[numThreads];
            for (int i = 0; i < numThreads; i++) {
                dequeueThreads[i] = new Thread(() -> {
                    for (int j = 0; j < tasksPerThread; j++) {
                        queue.dequeue();
                    }
                });
                dequeueThreads[i].start();
            }
            for (Thread t : dequeueThreads) {
                t.join();
            }
            assertTrue(queue.isEmpty()); //After all enqueues and dequeues, the queue should be empty.

        }
        @Test
        public void testConcurrency_multipleOperations() throws InterruptedException {
            final PriorityQueue<String> queue = new PriorityQueue<>();
            final int numThreads = 4;
            final int numOperations = 1000;

            Thread[] threads = new Thread[numThreads];

            for (int i = 0; i < numThreads; i++) {
                final int threadId = i;
                threads[i] = new Thread(() -> {
                    for (int j = 0; j < numOperations; j++) {
                        int operationType = j % 5; // Vary the operation

                        switch (operationType) {
                            case 0:
                                queue.enqueue(new Task<>("Task-" + threadId + "-" + j, j % 5));
                                break;
                            case 1:
                                queue.dequeue();
                                break;
                            case 2:
                                queue.contains(new Task<>("Task-" + threadId + "-" + (j % 3), j % 5)); //search for existing tasks
                                break;
                            case 3:
                                if (j % 2 == 0) {
                                    queue.updatePriority(new Task<>("Task-" + threadId + "-" + (j / 2), 1), j % 3); //update priority for existing tasks
                                }
                                break;
                            case 4:
                                queue.removeTask("Task-" + threadId + "-" + (j % 4)); //remove task
                                break;
                        }
                    }
                });
                threads[i].start();
            }

            for (Thread t : threads) {
                t.join();
            }
            // After all operations, it is difficult to determine the exact final state.  However,
            // We can check that basic operations still function correctly.
            queue.enqueue(new Task<>("FinalTask", 1));
            assertFalse(queue.isEmpty());
            assertEquals("FinalTask", queue.dequeue().getTask());
        }
}