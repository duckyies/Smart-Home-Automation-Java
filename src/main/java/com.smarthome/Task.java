package com.smarthome;
import java.util.Comparator;

public class Task<T> {

    protected T task;
    protected int priority;
    
    Task(T task, int priority) {
        this.task = task;
        this.priority = priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setTask(T task) {
        this.task = task;
    }

}

class TaskComparator<T> implements Comparator<Task<T>> {

    @Override
    public int compare(Task<T> t1, Task<T> t2) {
        return t1.priority - t2.priority;
    }
    
}