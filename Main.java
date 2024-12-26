public class Main {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        LinkedList ll = new LinkedList();
        ll.addFront();
        ll.addFront(34);
        ll.addFront(2);
        ll.addEnd(100);
        ll.addIndex(0,4);
        ll.printList();

    }
}