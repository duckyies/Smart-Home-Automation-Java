import java.util.ArrayList;
import java.util.List;
/*Smart Home Automation Control

a. Use Priority Queue for device management

b. Implement Linked List for automation rules

c. Energy-efficient device coordination */
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
        System.out.println(ll.isEmpty());

        ll.addFront(34);
        ll.addFront(2);
        //ll.addFront("Y");
        ll.addEnd(100);
        ll.printList();
        System.out.println(ll.makeArrayList());

        System.out.println(ll.contains(1000));
        ll.addIndex(0,1);
        ll.addIndex(69, 4);
        ll.addIndex(420,1);
        ll.printList();
        //System.out.println(ll.valueAt(7));
        ll.removeIndex(1);
        ll.printList();
        ll.reverse();
        ll.printList();
        ll.sort();
        ll.printList();
        System.out.println(ll.peek());
        System.out.println(ll.peekEnd());
        ll.set(0,1);
        ll.printList();

        System.out.println();
        System.out.println();

        ll.removeIndex(ll.indexOf(69));
        ll.printList();
    }
}