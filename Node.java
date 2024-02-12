
public class Node<T> {

    public T item;
    public Node<T> next;
    public int priority = 0;

    public Node(T item) {
        this.item = item;
    }
    
    public Node(T item, int pri) {
        this.item = item;
        this.priority = pri;
    }

    public T getItem() {
        return item;
    }
}
