import java.util.ArrayList;

public class PriorityQueue<T> {

    private ArrayList<Task<T>> queue;

    PriorityQueue() {
        this.queue = new ArrayList<Task<T>>();
    }

    PriorityQueue(Task<T> task) {
        this.queue = new ArrayList<Task<T>>();
        queue.add(task);
    }

    public void enqueue(Task<T> newTask) {
        for(Task<T> task : queue) {
            
        }
    }

    public void dequeue() {
        queue.remove(0);
    }
}
