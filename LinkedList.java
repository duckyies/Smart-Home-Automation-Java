import java.util.ArrayList;

public class LinkedList<T> {

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

    public void addFront(T val) {
        Node<T> newNode = new Node<T>(val);
        newNode.next = head;
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

    pu

// methods to add for now
    // linkedlist from array
    // remove from the front
    // remove from the end
    // remove from a specific index
    // get the size of the list
    // get the value at a specific index
    // check if the list is empty
    // clear the list
    // reverse the list
    // sort the list
    // check if the list contains a value
    // get the index of a value
}
