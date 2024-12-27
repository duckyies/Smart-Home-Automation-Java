import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LinkedList<T extends Comparable<T>> {

    private Node<T> head;

    private class Node<U> {

        private U val;
        private Node<U> next;

        Node(U val, Node<U> next) {
            this.val = val;
            this.next = next;
        }

        Node(U val) {
            this.val = val;
            this.next = null;
        }
    }

    LinkedList() {
        this.head = null;
    }

    LinkedList(T val) {
        this.head = new Node<T>(val);
    }

    LinkedList(T[] arr) {
        for (T val : arr) {
            addEnd(val);
        }
    }

    LinkedList(List<T> arr) {
        for (T val : arr) {
            addEnd(val);
        }
    }
 
    LinkedList(ArrayList<T> arr) {
        for (T val : arr) {
            addEnd(val);
        }
    }

    public void addFront(T val) {
        Node<T> newNode = new Node<T>(val, head);
        head = newNode;
    }

    public void addEnd(T val) {
        Node<T> newNode = new Node<T>(val);

        newNode.next = null;
        if (head == null) {
            head = newNode;
        }

        else {
            Node<T> temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
    }

    public void addIndex(T val, int index) {
        Node<T> newNode = new Node<T>(val);

        if(index == 0) {
            addFront(val);
            return;
        }
        Node<T> tempNode = head;

        while(index != 1) {
            if(tempNode.next == null) {
                throw new Error("Index out of bounds");
            }
            tempNode = tempNode.next;
            index--;
        }

        Node<T> temp = tempNode.next;
        tempNode.next = newNode;
        newNode.next = temp;

    }

    public void printList() {
        Node<T> temp = head;

        while (temp != null) {
            System.out.print(temp.val + " ");
            temp = temp.next;
        }
        System.out.println();
    }

    //very useless im dumb
    public void extendFromArray(T[] arr) {
        for (T val : arr) {
            addEnd(val);
        }
    }

    public int getSize() {
        Node<T> temp = head;
        int size = 0;

        while (temp != null) {
            size++;
            temp = temp.next;
        }

        return size;
    }

    public void removeFront() {
        if(head == null) {
            throw new Error("List is empty!");
        }

        head = head.next;
    }

    public void removeEnd() {
        if(head == null) {
            throw new Error("List is empty!");
        }

        Node<T> temp = head;

        while(temp.next.next != null) {
            temp = temp.next;
        }

        temp.next = null;
    }

    public Boolean isEmpty() {
        if(head == null) return true;
        return false;
    }

    //use for other methods because easy and im lazy
    public ArrayList<T> makeArrayList() {
        ArrayList<T> List = new ArrayList<T>();
        Node<T> temp = head;

        while(temp.next != null) {
            List.add(temp.val);
            temp = temp.next;
        }
        List.add(temp.val);
        return List;
    }

    public void clear() {
        head = null;        
    }

    public boolean contains(T value) {
        Node<T> temp = head;

        while (temp != null) {
            if (temp.val == value) {
                return true;
            }
            temp = temp.next;
        
        }
        return false;
    }

    public int indexOf(T value) {
        Node<T> temp = head;
        int index = 0;

        while (temp != null) {
            if (temp.val == value) {
                return index;
            }
            index++;
            temp = temp.next;
        
        }
        return -1;
    }

    public T valueAt(int index) {
        if(head == null) {
            throw new Error("List is empty!");
        }

        if(index == 0) {
            return head.val;
        }

        Node<T> tempNode = head.next;

        while(index != 1) {

            if(tempNode.next == null) {
                throw new Error("Index out of bounds");
            }
            tempNode = tempNode.next;
            index--;
        }
        
        return tempNode.val;
    }
    
    public void removeIndex(int index) {

        if(index == 0) {
            removeFront();
            return;
        }
        Node<T> tempNode = head;

        while(index != 1) {
            if(tempNode.next.next == null) {
                throw new Error("Index out of bounds");
            }
            index--;
            tempNode = tempNode.next;
        }

        if (tempNode.next.next == null) {
            removeEnd();
            return;
        }

        tempNode.next = tempNode.next.next;

    }

    //not that efficient but im lazy and it works
    public void reverse() {
        ArrayList<T> tempArray = makeArrayList();
        List<T> temp = tempArray.reversed();
        clear();

        for (T val: temp) {
            addEnd(val);
        }
    }
       
    public void sort() {

        ArrayList<T> tempArray = makeArrayList();
        tempArray.sort(Comparator.naturalOrder());
        clear();

        for (T val: tempArray) {
            addEnd(val);
        }
    }

}
