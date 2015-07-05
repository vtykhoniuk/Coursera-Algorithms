import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int N;
    private int M;
    private Item[] a;

    public RandomizedQueue()
    {
        a = (Item[]) new Object[4];
        N = 2;
        M = 1;
    }

    public Iterator<Item> iterator()
    {
        return new RandomizedQueueIterator();
    }

    public boolean isEmpty()
    {
        return size() == 0;
    }

    public int size()
    {
        return N - M - 1;
    }

    public void enqueue(Item item)
    {
        if (item == null)
            throw new NullPointerException();

        if (N == a.length)
            resize(4*a.length);

        a[N++] = item;
    }

    public Item dequeue()
    {
        if (isEmpty())
            throw new NoSuchElementException();

        int i = StdRandom.uniform(size());
        int k = M + i + 1;
        Item result = a[k];
        swap(++M, k);

        a[M] = null;

        if (size() < a.length/4)
            resize(a.length/2);

        return result;
    }

    private void resize(int capacity)
    {
        Item[] newA = (Item[]) new Object[capacity];

        int size = size();
        int i = M + 1;

        M = (capacity - size) / 2 - 1;
        N = M + 1;

        while (size-- > 0)
            newA[N++] = a[i++];

        a = newA;
    }

    public Item sample()
    {
        if (isEmpty())
            throw new NoSuchElementException();

        int i = StdRandom.uniform(size());

        return a[M+i+1];
    }

    private void swap(int i, int j)
    {
        Item tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static void main(String[] args)
    {
        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
        int N = 100;

        for (int i = 0; i < N; ++i) {
            System.out.println("Insert [" + i + "]");
            q.enqueue(i);
        }
        System.out.println("Size: " + q.size());

        System.out.println("----------");
        for (int i = 0; i < N; ++i)
            System.out.println(q.dequeue());
        System.out.println("Size: " + q.size());

        /*
        System.out.println("----------");
        for (int i = 0; i < N; ++i)
            q.enqueue(i);

        System.out.println("Size: " + q.size());
        for (int i : q)
            System.out.println(i);

        System.out.println("----------");
        for (int i = 0; i < N; ++i)
            System.out.println(q.sample());
        */
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] randA = null;
        private int currentIdx;

        RandomizedQueueIterator()
        {
            randA = (Item[]) new Object[size()];

            if (randA.length > 0) {
                for (int i = M+1, k = 0; i < N; ++i, ++k)
                    randA[k] = a[i];

                StdRandom.shuffle(randA);
            }

            currentIdx = 0;
        }

        public boolean hasNext()
        {
            return currentIdx < randA.length;
        }

        public Item next()
        {
            if (!hasNext())
                throw new NoSuchElementException();

            return randA[currentIdx++];
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }
}
