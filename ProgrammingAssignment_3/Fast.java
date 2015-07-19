public class Fast {
    public static void main(String[] args) {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);

        // read in the input
        String filename = args[0];
        In in = new In(filename);

        int N = in.readInt();
        if (N < 4)
            return;

        Point[] a = new Point[N];
        Point p = null;
        StdDraw.setPenRadius(0.001);  // make the points a bit larger
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            a[i] = new Point(x, y);
            a[i].draw();

            if (p == null || a[i].compareTo(p) < 0)
                p = a[i];
        }

        // 'p' now is the minimal point of array 'a'

        while (p != null) {
            java.util.Arrays.sort(a, p.SLOPE_ORDER);

            // Slide through simmilar points
            int l = 0;
            while (a[++l] == p)
                if (l == N-1)
                    break;

            // From that point we need to accomplish two tasks:
            // 1. Find the next 'p', which is smallest point among all
            //    points that not smaller than current 'p'
            // 2. Find 4 or more points on the same line

            Point newP = null;
            int k = l;
            for (; l < N; ++l) {
                // Searching for a new minimum
                if (a[l].compareTo(p) > 0
                    && (newP == null || a[l].compareTo(newP) < 0))
                    newP = a[l];

                if (p.slopeTo(a[k]) != p.slopeTo(a[l])) {
                    if (l - k + 1 > 3)
                        printAndDraw(a, k, l, p);

                    k = l;
                }
            }

            if (l - k + 1 > 3)
                printAndDraw(a, k, l, p);

            p = newP;
        }

        StdDraw.show(0);
    }

    private static void printAndDraw(Point[] a, int k, int l, Point p) {
        java.util.Arrays.sort(a, k, l);
        if (p.compareTo(a[k]) < 0) {
            p.drawTo(a[l-1]);

            System.out.print(p);
            for (int i = k; i < l; ++i)
                System.out.print(" -> " + a[i]);
            System.out.println();
        }
    }
}
