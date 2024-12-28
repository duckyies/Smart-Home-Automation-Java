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

        if(newTask.priority < queue.getFirst().priority) {
            queue.addFirst(newTask);
            return;
        }

        if(newTask.priority >= queue.getLast().priority) {
            queue.addLast(newTask);
            return;
        }

        for(int i = 0; i < queue.size(); i++) {
            
            if(queue.get(i).priority > newTask.priority) {
                queue.add(i - 1, newTask);
                break;
            }
        }
    }

    public void dequeue() {
        queue.remove(0);
    }
}
