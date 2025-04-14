package com.smarthome.datastuctures;
import com.smarthome.misc.EmptyListAccessException;
import java.util.concurrent.locks.ReentrantLock;


/**
 * A linked list implementation.
 *
 * @param <T> the type of elements stored in the linked list.
 */
public class LinkedList<T extends Comparable<T>> {
    private final ReentrantLock lock = new ReentrantLock();
    private Node<T> head;

    /**
     * A node in the linked list. Each node contains a value and a reference to the next node.
     *
     * @param <U> the type of the value stored in the node.
     */
    private static class Node<U> {

        private U val;
        private Node<U> next;

        /**
         * Constructs a new node with the given value and next node.
         *
         * @param val  the value to store in the node.
         * @param next the next node in the list.
         */
        Node(U val, Node<U> next) {
            this.val = val;
            this.next = next;
        }

        /**
         * Constructs a new node with the given value and no next node.
         *
         * @param val the value to store in the node.
         */
        Node(U val) {
            this.val = val;
            this.next = null;
        }
    }

    /**
     * Constructs an empty linked list.
     */
    public LinkedList() {
        this.head = null;
    }

    /**
     * Adds a new element to the beginning of the list.
     *
     * @param val the value to add to the front of the list.
     */
    public void addFront(T val) {
        lock.lock();
        try {
            head = new Node<>(val, head);
        }
        finally {
            lock.unlock();
        }
    }

    /**
     * Retrieves the value of the first element in the list without removing it.
     *
     * @return the value of the first element.
     */
    public T peek() {
        if(head == null) {
            return null;
        }
        lock.lock();
        try {
            return head.val;
        }
        finally {
            lock.unlock();
        }
    }

    /**
     * Retrieves and removes the value of the first element in the list.
     *
     * @return the value of the first element.
     */
    public T peekAndRemove() {
        T val;
        if (head == null) {
            return null;
        }
        lock.lock();
        try {
            val = head.val;
            head = head.next;
        }
        finally {
            lock.unlock();
        }
        return val;
    }


    /**
     * Retrieves and removes the value of the last element in the list.
     *
     * @return the value of the last element.
     */
    public T peekEndAndRemove() {
        if (head == null) {
            return null;
        }
        Node<T> temp = head;
        Node<T> prev = null;
        T val;
        lock.lock();
        try {
            while (temp.next != null) {
                prev = temp;
                temp = temp.next;
            }
            val = temp.val;
            if (prev == null) {
                head = null;
            } else {
                prev.next = null;
            }
        } finally {
            lock.unlock();

        }
        return val;
    }


    /**
     * Retrieves the value of the last element in the list without removing it.
     *
     * @return the value of the last element.
     * @throws EmptyListAccessException if the list is empty.
     */
    public T peekEnd() {
        if (head == null) {
            throw new EmptyListAccessException("List is empty!");
        }

        Node<T> temp = head;

        lock.lock();
        try{
            while (temp.next != null) {
                temp = temp.next;
            }

            return temp.val;
        }
        finally {
            lock.unlock();
        }
    }

    /**
     * Adds a new element to the end of the list.
     *
     * @param val the value to add to the end of the list.
     */
    public void addEnd(T val) {
        Node<T> newNode = new Node<>(val);
        newNode.next = null;

        lock.lock();
        try {
            if (head == null) {
                head = newNode;
            } else {
                Node<T> temp = head;
                while (temp.next != null) {
                    temp = temp.next;
                }
                temp.next = newNode;
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Adds a new element at a specific position in the list.
     *
     * @param val   the value to add to the list.
     * @param position the position at which to insert the new element.
     * @throws IndexOutOfBoundsException if the position is out of bounds.
     */
    public void addPosition(T val, int position) {
        Node<T> newNode = new Node<>(val);

        if (position == 0) {
            addFront(val);
            return;
        }
        Node<T> tempNode = head;

        lock.lock();
        try {
            if (tempNode == null) {
                throw new IndexOutOfBoundsException("Position out of bounds");
            }
            while (position != 1) {
                if (tempNode.next == null) {
                    throw new IndexOutOfBoundsException("Position out of bounds");
                }
                tempNode = tempNode.next;
                position--;
            }

            Node<T> temp = tempNode.next;
            tempNode.next = newNode;
            newNode.next = temp;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns the number of elements in the list.
     *
     * @return the size of the list.
     */
    public int getSize() {
        Node<T> temp = head;
        int size = 0;

        lock.lock();
        try {
            while (temp != null) {
                size++;
                temp = temp.next;
            }

            return size;
        }
        finally {
            lock.unlock();
        }
    }

    /**
     * Sets the value of the element at a specific position.
     *
     * @param position the position of the element to update.
     * @param value the new value for the element.
     * @throws EmptyListAccessException if the list is empty.
     * @throws IndexOutOfBoundsException if the position is out of bounds.
     */
    public void set(int position, T value) {
        if (head == null) {
            throw new EmptyListAccessException("List is empty!");
        }

        if (position == 0) {
            head.val = value;
            return;
        }
        Node<T> tempNode = head;

        lock.lock();
        try {
            while (position != 1) {
                if (tempNode.next.next == null) {
                    throw new IndexOutOfBoundsException("Position out of bounds");
                }
                tempNode = tempNode.next;
                position--;
            }

            tempNode.next.val = value;
        }
        finally {
            lock.unlock();
        }
    }

    /**
     * Checks if the list is empty.
     *
     * @return {@code true} if the list contains no elements, {@code false} otherwise.
     */
    public Boolean isEmpty() {
        return head == null;
    }


    /**
     * Removes all elements from the listy.
 */
    public void clear() {
        lock.lock();
        try {
            head = null;
        }
        finally {
            lock.unlock();
        }
    }

    /**
     * Checks if the list contains a specific value.
     *
     * @param value the value to search for.
     * @return {@code true} if the list contains the specified value, {@code false} otherwise.
     */
    public boolean contains(T value) {
        Node<T> temp = head;

        lock.lock();
        try {
            while (temp != null) {
                if (temp.val.equals(value)) {
                    return true;
                }
                temp = temp.next;

            }
            return false;
        }
        finally {
            lock.unlock();
        }
    }

    /**
     * Returns the value of the element at a specific position.
     *
     * @param position the position of the element to retrieve.
     * @return the value of the element at the specified position.
     * @throws EmptyListAccessException if the list is empty/
     * @throws IndexOutOfBoundsException if the position is out of bounds.
     */
    public T get(int position) {
        if (head == null) {
            throw new EmptyListAccessException("List is empty!");
        }

        if (position == 0) {
            return head.val;
        }

        Node<T> tempNode = head;
        int currentPosition = 0;

        lock.lock();
        try {
            while (tempNode != null) {
                if (currentPosition == position) {
                    return tempNode.val;
                }
                tempNode = tempNode.next;
                currentPosition++;
            }

            throw new IndexOutOfBoundsException("Position out of bounds");
        }
        finally {
            lock.unlock();
        }
    }

    /**
     * Removes the element at a specific position from the list.
     *
     * @param position the position of the element to remove.
     * @throws IndexOutOfBoundsException if the position is out of bounds.
     */
    public void removePosition(int position) {

        if (position == 0) {
            peekAndRemove();
            return;
        }
        Node<T> tempNode = head;
        int currentPosition = 0;

        lock.lock();
        try {
            while (tempNode != null && currentPosition < position - 1) {
                tempNode = tempNode.next;
                currentPosition++;
            }

            if (tempNode == null || tempNode.next == null) {
                throw new IndexOutOfBoundsException("Position out of bounds");
            }

            tempNode.next = tempNode.next.next;
        }
        finally {
            lock.unlock();
        }
    }
}