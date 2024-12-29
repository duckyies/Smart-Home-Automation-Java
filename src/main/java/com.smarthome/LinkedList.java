package com.smarthome;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * A linked list implementation.
 *
 * @param <T> the type of elements stored in the linked list.
 */
public class LinkedList<T extends Comparable<T>> {

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
    LinkedList() {
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
     * Retrieves the value of the first element in the list without removing it.
     *
     * @return the value of the first element.
     */
    public T peek() {
        return head.val;
    }

    /**
     * Retrieves the value of the last element in the list without removing it.
     *
     * @return the value of the last element.
     * @throws Error if the list is empty.
     */
    public T peekEnd() {
        if (head == null) {
            throw new Error("List is empty!");
        }

        Node<T> temp = head;

        while (temp.next != null) {
            temp = temp.next;
        }
        return temp.val;
    }

    /**
     * Adds a new element to the beginning of the list.
     *
     * @param val the value to add to the front of the list.
     */
    public void addFront(T val) {
        Node<T> newNode = new Node<>(val, head);
        head = newNode;
    }

    /**
     * Adds a new element to the end of the list.
     *
     * @param val the value to add to the end of the list.
     */
    public void addEnd(T val) {
        Node<T> newNode = new Node<>(val);

        newNode.next = null;
        if (head == null) {
            head = newNode;
        } else {
            Node<T> temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
    }

    /**
     * Adds a new element at a specific index in the list.
     *
     * @param val   the value to add to the list.
     * @param index the index at which to insert the new element.
     * @throws Error if the index is out of bounds.
     */
    public void addIndex(T val, int index) {
        Node<T> newNode = new Node<>(val);

        if (index == 0) {
            addFront(val);
            return;
        }
        Node<T> tempNode = head;

        while (index != 1) {
            if (tempNode.next == null) {
                throw new Error("Index out of bounds");
            }
            tempNode = tempNode.next;
            index--;
        }

        Node<T> temp = tempNode.next;
        tempNode.next = newNode;
        newNode.next = temp;

    }

    /**
     * Prints the elements of the list to the console.
     */
    public void printList() {
        Node<T> temp = head;

        while (temp != null) {
            System.out.print(temp.val + " ");
            temp = temp.next;
        }
        System.out.println();
    }

    /**
     * Extends the list by adding elements from an array to the end of the list.
     *
     * @param arr the array of elements to add.
     */
    public void extendFromArray(T[] arr) {
        for (T val : arr) {
            addEnd(val);
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

        while (temp != null) {
            size++;
            temp = temp.next;
        }

        return size;
    }

    /**
     * Sets the value of the element at a specific index.
     *
     * @param index the index of the element to update.
     * @param value the new value for the element.
     * @throws Error if the list is empty or the index is out of bounds.
     */
    public void set(int index, T value) {
        if (head == null) {
            throw new Error("List is empty!");
        }

        if (index == 0) {
            head.val = value;
            return;
        }
        Node<T> tempNode = head;

        while (index != 1) {
            if (tempNode.next.next == null) {
                throw new Error("Index out of bounds");
            }
            tempNode = tempNode.next;
            index--;
        }

        tempNode.next.val = value;

    }

    /**
     * Removes the first element from the list.
     *
     * @throws Error if the list is empty.
     */
    public void removeFront() {
        if (head == null) {
            throw new Error("List is empty!");
        }

        head = head.next;
    }

    /**
     * Removes the last element from the list.
     *
     * @throws Error if the list is empty.
     */
    public void removeEnd() {
        if (head == null) {
            throw new Error("List is empty!");
        }

        Node<T> temp = head;

        while (temp.next.next != null) {
            temp = temp.next;
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

        while (temp != null) {
            list.add(temp.val);
            temp = temp.next;
        }
        return list;
    }

    /**
     * Removes all elements from the listy.
     */
    public void clear() {
        head = null;
    }

    /**
     * Checks if the list contains a specific value.
     *
     * @param value the value to search for.
     * @return {@code true} if the list contains the specified value, {@code false} otherwise.
     */
    public boolean contains(T value) {
        Node<T> temp = head;

        while (temp != null) {
            if (temp.val.equals(value)) {
                return true;
            }
            temp = temp.next;

        }
        return false;
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

        while (temp != null) {
            if (temp.val.equals(value)) {
                return index;
            }
            index++;
            temp = temp.next;

        }
        return -1;
    }

    /**
     * Returns the value of the element at a specific index.
     *
     * @param index the index of the element to retrieve.
     * @return the value of the element at the specified index.
     * @throws Error if the list is empty or the index is out of bounds.
     */
    public T get(int index) {
        if (head == null) {
            throw new Error("List is empty!");
        }

        if (index == 0) {
            return head.val;
        }

        Node<T> tempNode = head;
        int currentIndex = 0;

        while (tempNode != null) {
            if (currentIndex == index) {
                return tempNode.val;
            }
            tempNode = tempNode.next;
            currentIndex++;
        }

        throw new Error("Index out of bounds");
    }

    /**
     * Removes the element at a specific index from the list.
     *
     * @param index the index of the element to remove.
     * @throws Error if the index is out of bounds.
     */
    public void removeIndex(int index) {

        if (index == 0) {
            removeFront();
            return;
        }
        Node<T> tempNode = head;
        int currentIndex = 0;

        while (tempNode != null && currentIndex < index - 1) {
            tempNode = tempNode.next;
            currentIndex++;
        }

        if (tempNode == null || tempNode.next == null) {
            throw new Error("Index out of bounds");
        }

        tempNode.next = tempNode.next.next;

    }

    /**
     * Reverses the order of the elements in the list.
     */
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