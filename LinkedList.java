
    public class LinkedList<Item>{

        Node<Item> first, last;
        int size = 0;

        public LinkedList() {
            first = null;
            last = null;
            size = 0;
        }      

        // prints the list
        public String toString() {
            Node tmp = first;
            String str = "List with " + size + " elements: ";

            while (tmp != null) {
                str += tmp.toString() + "->";
                tmp = tmp.next;
            }
            str +=".";
            //str +="\nfirst: "+first.toString()+ " last: "+last.toString();
            return str;
        }


          public void insertFirst(Item x) {
        Node<Item> newNode = new Node<Item>(x);
            if (first == null) {
                first = newNode;
                last = newNode;
            } else {
                newNode.next = first;
                first = newNode;
            }
            size++;
        } 


        public void insertAfter(Node<Item> p, Item x) {
        Node<Item> newNode = new Node<Item>(x);
            if (p == null || size == 0) {

                return;
            }
            if (p==last){
                insertLast(x);
                return;
            }
            newNode.next = p.next;
            p.next = newNode;
            size++;
        }

        public void insertLast(Item x) {
            Node newNode = new Node(x);
            if (first == null) {
                first = newNode;
                last = newNode;
            } else {
                last.next = newNode;
                last = newNode;
            }
            size++;
        }

         public boolean isEmpty() {
            return size == 0;
        }

         public Node removeFirst() {
            if (isEmpty()) {
                return null;
            }
            Node tmp = first;
            first = first.next;
            size--;
            return tmp;
        }
         
    }

