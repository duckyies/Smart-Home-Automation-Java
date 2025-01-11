package com.smarthome.datastuctures;
import com.smarthome.misc.EmptyListAccessException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
    private class Node<U> {

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
     * Constructs a linked list with a single element.
     *
     * @param val the value of the first element in the list.
     */
    LinkedList(T val) {
        this.head = new Node<>(val);
    }

    /**
     * Constructs a linked list from an array of elements.
     *
     * @param arr the array of elements to add to the list.
     */
    LinkedList(T[] arr) {
        for (T val : arr) {
            addEnd(val);
        }
    }

    /**
     * Constructs a linked list from a {@link List} of elements
     *
     * @param arr the list of elements to add to the linked list.
     */
    LinkedList(List<T> arr) {
        for (T val : arr) {
            addEnd(val);
        }
    }

    /**
     * Constructs a linked list from an {@link ArrayList} of elements.
     *
     * @param arr the array list of elements to add to the linked list.
     */
    LinkedList(ArrayList<T> arr) {
        for (T val : arr) {
            addEnd(val);
        }
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
     * Adds a new element at a specific index in the list.
     *
     * @param val   the value to add to the list.
     * @param index the index at which to insert the new element.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public void addIndex(T val, int index) {
        Node<T> newNode = new Node<>(val);

        if (index == 0) {
            addFront(val);
            return;
        }
        Node<T> tempNode = head;

        lock.lock();
        try {
            if (tempNode == null) {
                throw new IndexOutOfBoundsException("Index out of bounds");
            }
            while (index != 1) {
                if (tempNode.next == null) {
                    throw new IndexOutOfBoundsException("Index out of bounds");
                }
                tempNode = tempNode.next;
                index--;
            }

            Node<T> temp = tempNode.next;
            tempNode.next = newNode;
            newNode.next = temp;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Prints the elements of the list to the console.
     */
    public void printList() {
        Node<T> temp = head;

        lock.lock();
        try {
            while (temp != null) {
                System.out.print(temp.val + " ");
                temp = temp.next;
            }
        }
        finally {
            System.out.println();
            lock.unlock();
        }
    }

    /**
     * Extends the list by adding elements from an array to the end of the list.
     *
     * @param arr the array of elements to add.
     */
    public void extendFromArray(T[] arr) {
        lock.lock();
        try {
            for (T val : arr) {
                addEnd(val);
            }
        }
        finally {
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
     * Sets the value of the element at a specific index.
     *
     * @param index the index of the element to update.
     * @param value the new value for the element.
     * @throws EmptyListAccessException if the list is empty.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public void set(int index, T value) {
        if (head == null) {
            throw new EmptyListAccessException("List is empty!");
        }

        if (index == 0) {
            head.val = value;
            return;
        }
        Node<T> tempNode = head;

        lock.lock();
        try {
            while (index != 1) {
                if (tempNode.next.next == null) {
                    throw new IndexOutOfBoundsException("Index out of bounds");
                }
                tempNode = tempNode.next;
                index--;
            }

            tempNode.next.val = value;
        }
        finally {
            lock.unlock();
        }
    }

    /**
     * Removes the first element from the list.
     *
     * @throws EmptyListAccessException if the list is empty.
     */
    public void removeFront() {
        if (head == null) {
            throw new EmptyListAccessException("List is empty!");
        }
        lock.lock();
        try {
            head = head.next;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Removes the last element from the list.
     *
     * @throws EmptyListAccessException if the list is empty.
     */
    public void removeEnd() {
        if (head == null) {
            throw new EmptyListAccessException("List is empty!");
        }

        Node<T> temp = head;

        lock.lock();
        try {
            while (temp.next.next != null) {
                temp = temp.next;
            }
        }
        finally {
            lock.unlock();
        }
        temp.next = null;
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
     * Creates and returns an {@link ArrayList} containing the elements of this linked list in the same order.
     *
     * @return an {@link ArrayList} representation of the linked list.
     */
    public ArrayList<T> makeArrayList() {
        ArrayList<T> list = new ArrayList<>();
        Node<T> temp = head;

        lock.lock();
        try {
            while (temp != null) {
                list.add(temp.val);
                temp = temp.next;
            }
            return list;
        }
        finally {
            lock.unlock();
        }
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
     * Returns the index of the first occurrence of a specific value in the list.
     *
     * @param value the value to search for.
     * @return the index of the first occurrence of the value, or -1 if the value is not found.
     */
    public int indexOf(T value) {
        Node<T> temp = head;
        int index = 0;

        lock.lock();
        try {
            while (temp != null) {
                if (temp.val.equals(value)) {
                    return index;
                }
                index++;
                temp = temp.next;

            }
            return -1;
        }
        finally {
            lock.unlock();
        }
    }

    /**
     * Returns the value of the element at a specific index.
     *
     * @param index the index of the element to retrieve.
     * @return the value of the element at the specified index.
     * @throws EmptyListAccessException if the list is empty/
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public T get(int index) {
        if (head == null) {
            throw new EmptyListAccessException("List is empty!");
        }

        if (index == 0) {
            return head.val;
        }

        Node<T> tempNode = head;
        int currentIndex = 0;

        lock.lock();
        try {
            while (tempNode != null) {
                if (currentIndex == index) {
                    return tempNode.val;
                }
                tempNode = tempNode.next;
                currentIndex++;
            }

            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        finally {
            lock.unlock();
        }
    }

    /**
     * Removes the element at a specific index from the list.
     *
     * @param index the index of the element to remove.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public void removeIndex(int index) {

        if (index == 0) {
            removeFront();
            return;
        }
        Node<T> tempNode = head;
        int currentIndex = 0;

        lock.lock();
        try {
            while (tempNode != null && currentIndex < index - 1) {
                tempNode = tempNode.next;
                currentIndex++;
            }

            if (tempNode == null || tempNode.next == null) {
                throw new IndexOutOfBoundsException("Index out of bounds");
            }

            tempNode.next = tempNode.next.next;
        }
        finally {
            lock.unlock();
        }
    }

    /**
     * Reverses the order of the elements in the list.
     */
    //these two should technically work synchronously but idk
    public void reverse() {
        ArrayList<T> tempArray = makeArrayList();
        List<T> temp = tempArray.reversed();
        clear();

        for (T val : temp) {
            addEnd(val);
        }
    }

    /**
     * Sorts the elements of the list in their natural ascending order.
     */
    public void sort() {
        ArrayList<T> tempArray = makeArrayList();
        tempArray.sort(Comparator.naturalOrder());
        clear();

        for (T val : tempArray) {
            addEnd(val);
        }
    }
}