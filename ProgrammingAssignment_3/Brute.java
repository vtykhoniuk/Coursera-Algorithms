public class Brute {
    public static void main(String[] args) {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.001);  // make the points a bit larger

        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] a = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            a[i] = new Point(x, y);
            a[i].draw();
        }

        Point[] b = new Point[4];
        for (int i = 0; i < N - 3; ++i) {
            for (int j = i+1; j < N - 2; ++j) {
                double slope = a[i].slopeTo(a[j]);

                for (int k = j+1; k < N - 1; ++k) {
                    if (slope != a[i].slopeTo(a[k]))
                        continue;

                    for (int l = k+1; l < N; ++l) {
                        if (slope != a[i].slopeTo(a[l]))
                            continue;

                        b[0] = a[i];
                        b[1] = a[j];
                        b[2] = a[k];
                        b[3] = a[l];

                        java.util.Arrays.sort(b);
                        System.out.println(b[0] + " -> "
                            + b[1] + " -> " + b[2] + " -> " + b[3]);

                        b[0].drawTo(b[3]);
                    }
                }
            }
        }

        StdDraw.show(0);
    }
}
