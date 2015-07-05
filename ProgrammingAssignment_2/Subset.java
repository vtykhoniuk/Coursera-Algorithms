import java.util.Iterator;

public class Subset {
    public static void main(String[] args)
    {
        int k = new Integer(args[0]).intValue();

        if (k == 0)
            return;

        RandomizedQueue<String> q = new RandomizedQueue<String>();
        while (!StdIn.isEmpty())
            q.enqueue(StdIn.readString());

        while (q.size() > 0 && k-- > 0)
            System.out.println(q.dequeue());
    }
}
