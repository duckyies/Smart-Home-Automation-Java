import java.util.ArrayList;
import java.util.List;

public class Main {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        LinkedList ll = new LinkedList(new ArrayList<>(List.of(1,2,3,4,5,6,7,8,9,10)));
        ll.addFront("Y");
        ll.addFront(34);
        ll.addFront(2);
        ll.addEnd(100);
        ll.addIndex(0,4);
        ll.extendFromArray(new Integer[]{1,2,3,4,5,6,7,8,9,10});
        ll.printList();

    }
}