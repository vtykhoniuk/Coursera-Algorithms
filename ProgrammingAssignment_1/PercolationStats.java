public class PercolationStats {
    private double MEAN;
    private double STDDEV;
    private double CLO;
    private double CHI;

    public PercolationStats(int N, int T)
    {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        double[] X = new double[T];
        double meanX = 0;
        double devidend = 0;

        for (int i = 0; i < X.length; ++i) {
            Percolation p = new Percolation(N);
            int opennedSites = 0;

            do {
                int x, y;

                do {
                    x = StdRandom.uniform(1, N+1);
                    y = StdRandom.uniform(1, N+1);
                } while (p.isOpen(x, y));

                p.open(x, y);
                opennedSites++;
            } while (!p.percolates());

            X[i] = (double) opennedSites/(N*N);
            meanX += X[i];
        }

        MEAN = meanX/T;

        for (int i = 0; i < X.length; ++i) {
            devidend += (X[i] - MEAN)*(X[i] - MEAN);
        }
        STDDEV = devidend/(X.length-1);

        CLO = MEAN-1.96*MEAN/java.lang.Math.sqrt(T);
        CHI = MEAN+1.96*MEAN/java.lang.Math.sqrt(T);
    }

    public double mean()
    {
        return MEAN;
    }

    public double stddev()
    {
        return STDDEV;
    }

    public double confidenceLo()
    {
        return CLO;
    }

    public double confidenceHi()
    {
        return CHI;
    }

    public static void main(String[] args)
    {
        int N = java.lang.Integer.decode(args[0]).intValue();
        int T = java.lang.Integer.decode(args[1]).intValue();

        PercolationStats stats = new PercolationStats(N, T);

        System.out.println("mean\t\t\t= " + stats.mean());
        System.out.println("stddev\t\t\t= " + stats.stddev());
        System.out.println("95% confidence interval\t= "
        + stats.confidenceLo() + " " + stats.confidenceHi());
    }
}
