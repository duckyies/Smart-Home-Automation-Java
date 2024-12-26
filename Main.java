import java.util.ArrayList;
import java.util.List;

public class Main {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        LinkedList ll = new LinkedList();
        System.out.println(ll.isEmpty());
        ll.addFront("Y");
        ll.addFront(34);
        ll.addFront(2);
        ll.addEnd(100);
        ll.addIndex(0,4);
      //  ll.removeFront();
        ll.removeEnd();
        System.out.println(ll.isEmpty());
        ll.printList();
        ll.clear();
        ll.addFront(10);
        ll.printList();
        System.out.println();
        System.out.println();

        ll.addFront(34);
        ll.addFront(2);
        ll.addFront("Y");
        ll.addEnd(100);
        ll.printList();
        System.out.println(ll.makeArrayList());
    }
}