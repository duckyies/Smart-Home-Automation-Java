package com.smarthome.tasks;
import java.util.Comparator;

/**
 * Represents a task with an associated priority.
 *
 * @param <T> The type of the task.
 */
public class Task<T> {

    /**
     * The task itself.
     */
    protected T task;
    /**
     * The priority of the task. Lower values indicate higher priority.
     */
    protected int priority;

    /**
     * Constructs a new Task with the specified task and priority.
     *
     * @param task     The task to be associated with this object.
     * @param priority The priority of the task.
     */
    Task(T task, int priority) {
        this.task = task;
        this.priority = priority;
    }

    /**
     * Sets the priority of the task.
     *
     * @param priority The new priority for the task.
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * Sets the task associated with this object.
     *
     * @param task The new task to be associated with this object.
     */
    public void setTask(T task) {
        this.task = task;
    }

    /**
     * Returns the task associated with this object.
     *
     * @return The task.
     */
    public T getTask() {
        return task;
    }

    /**
     * Returns the priority of the task.
     *
     * @return The priority of the task.
     */
    public int getPriority() {
        return priority;
    }

    public String toString() {
        return "Task: " + task + ", Priority: " + priority;
    }
}

/**
 * A comparator for comparing two {@link Task} objects based on their priority.
 * Tasks with lower priority values are considered to have higher priority.
 *
 * @param <T> The type of the task being compared.
 */
class TaskComparator<T> implements Comparator<Task<T>> {

    /**
     * Compares two {@link Task} objects based on their priority.
     *
     * @param t1 The first {@link Task} to compare.
     * @param t2 The second {@link Task} to compare.
     * @return A negative integer, zero, or a positive integer as the priority of the first task
     *         is less than, equal to, or greater than the priority of the second task.
     */
    @Override
    public int compare(Task<T> t1, Task<T> t2) {
        return t1.priority - t2.priority;
    }

}