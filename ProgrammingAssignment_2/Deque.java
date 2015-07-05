import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        private Node prev = null;
        private Node next = null;
        private Item value = null;

        Node(Node prev, Node next, Item value)
        {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }

        Node prev()  {   return prev;  }
        Node next()  {   return next;  }
        Item value() {   return value; }
    }

    private int N = 0;
    private Node first = null;
    private Node last = null;

    public Deque()
    {
    }

    public Iterator<Item> iterator()
    {
        return new DequeIterator();
    }

    public boolean isEmpty()
    {
        return N == 0;
    }

    public int size()
    {
        return N;
    }

    public void addFirst(Item item)
    {
        if (item == null)
            throw new NullPointerException();

        Node newNode = new Node(null, first, item);

        if (N++ == 0) {
            first = newNode;
            last = first;
            return;
        }

        first.prev = newNode;

        first = newNode;
    }

    public void addLast(Item item)
    {
        if (item == null)
            throw new NullPointerException();

        Node newNode = new Node(last, null, item);

        if (N++ == 0) {
            last = newNode;
            first = last;
            return;
        }

        last.next = newNode;

        last = newNode;
    }

    public Item removeFirst()
    {
        if (isEmpty())
            throw new NoSuchElementException();

        Item result = first.value;
        first = first.next;

        if (first != null)
            first.prev = null;
        else
            last = null;

        --N;

        return result;
    }

    public Item removeLast()
    {
        if (isEmpty())
            throw new NoSuchElementException();

        Item result = last.value;
        last = last.prev;

        if (last != null)
            last.next = null;
        else
            first = null;

        --N;

        return result;
    }

    public static void main(String[] args)
    {
        Deque<Integer> d = new Deque<Integer>();

        d.addFirst(1);
        d.addFirst(2);
        System.out.println(d.removeFirst());
        System.out.println("Size: " + d.size());
        System.out.println(d.removeLast());
        System.out.println("Size: " + d.size());

        for (int i : d)
            System.out.println(i);
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext()
        {
            return current != null;
        }

        public Item next()
        {
            if (!hasNext())
                throw new NoSuchElementException();

            Item result = current.value;
            current = current.next;

            return result;
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }
}
