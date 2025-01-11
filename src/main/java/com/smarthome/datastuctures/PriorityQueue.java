package com.smarthome.datastuctures;
import com.smarthome.misc.EmptyListAccessException;
import com.smarthome.tasks.Task;

import java.util.ArrayList;

/**
 * A generic priority queue implementation using an ArrayList.
 * Elements are ordered based on their priority, with lower priority values
 * indicating higher priority.
 *
 * @param <T> The type of the task associated with each priority.
 */
public class PriorityQueue<T> {

    private ArrayList<Task<T>> queue;

    /**
     * Constructs an empty priority queue.
     */
    public PriorityQueue() {
        this.queue = new ArrayList<Task<T>>();
    }

    /**
     * Constructs a priority queue with an initial task.
     *
     * @param task The initial task to add to the queue.
     */
    PriorityQueue(Task<T> task) {
        this.queue = new ArrayList<Task<T>>();
        queue.add(task);
    }

    /**
     * Adds a new task to the priority queue, maintaining the priority order.
     *
     * @param newTask The task to be added to the queue.
     */
    public void enqueue(Task<T> newTask) {

        if (queue.isEmpty()) {
            queue.add(newTask);
            return;
        }
        if(newTask.getPriority() < queue.getFirst().getPriority()) {
            queue.addFirst(newTask);
            return;
        }

        if(newTask.getPriority() >= queue.getLast().getPriority()) {
            queue.addLast(newTask);
            return;
        }
        for(int i = 0; i < queue.size(); i++) {

            if(queue.get(i).getPriority() > newTask.getPriority()) {
                queue.add(i , newTask);
                break;
            }
        }
    }

    /**
     * Removes the highest priority task from the queue (the task at the front of the queue).
     * @throws EmptyListAccessException if the queue is empty.
     */
    public void dequeue() {
        if(queue.isEmpty()) {
            throw new EmptyListAccessException("The queue is empty");
        }
        queue.removeFirst();
    }

    /**
     * Prints the priority and task of each element in the queue to the console.
     */
    public void print() {
        for(Task<T> task : queue) {
            System.out.printf("Priority: %d, Task: %s\n", task.getPriority(), task.getTask());
        }
    }

    /**
     * Returns the highest priority task in the queue without removing it.
     *
     * @return The highest priority task, or null if the queue is empty.
     */
    public Task<T> peek() {
        if(queue.isEmpty()) {
            return null;
        }
        return queue.getFirst();
    }

    /**
     * Returns a new ArrayList containing all the tasks currently in the priority queue.
     *
     * @return A new ArrayList containing all the tasks in the queue.
     */
    public ArrayList<Task<T>> getQueue() {
        return new ArrayList<>(queue);
    }

    /**
     * Returns the number of tasks currently in the priority queue.
     *
     * @return The size of the queue.
     */
    public int size() {
        return queue.size();
    }

    /**
     * Checks if the priority queue is empty.
     *
     * @return True if the queue contains no tasks, false otherwise.
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * Removes all tasks from the priority queue, making it empty.
     */
    public void clear() {
        queue.clear();
    }

    /**
     * Checks if the priority queue contains a specific task.
     *
     * @param task The task to search for.
     * @return True if the queue contains the specified task, false otherwise.
     */
    public boolean contains(Task<T> task) {
        return queue.contains(task);
    }

    /**
     * Updates the priority of an existing task in the queue.
     *
     * @param task     The task whose priority needs to be updated.
     * @param priority The new priority for the task.
     */
    public void updatePriority(Task<T> task, int priority) {
        if(queue.remove(task)) {
            task.setPriority(priority);
            enqueue(task);
        }
    }

    /**
     * Retrieves the priority of a task in the queue based on its task content.
     *
     * @param task The task content to search for.
     * @return The priority of the task if found, or -1 if the task is not in the queue.
     */
    public int getPriority(T task) {
        for(Task<T> task1 : queue) {
            if(task1.getTask().equals(task)) {
                return task1.getPriority();
            }
        }
        return -1;
    }

    /**
     * Retrieves the first task in the queue with the specified priority.
     *
     * @param priority The priority to search for.
     * @return The first task with the specified priority, or null if no task with that priority exists.
     */
    public Task<T> getTask(int priority) {
        for(Task<T> task1 : queue) {
            if(task1.getPriority() == priority) {
                return task1;
            }
        }
        return  null;
    }
}