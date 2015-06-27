public class Percolation {
    private WeightedQuickUnionUF UF;
    private boolean[] STATE;
    private int N;

    public Percolation(int N)
    {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        this.N = N;

        STATE = new boolean[N*N+2];
        UF = new WeightedQuickUnionUF(STATE.length);

        // Two virtual nodes (upper and bottom) that are always openned
        STATE[0] = true;
        STATE[1] = true;
    }

    public void open(int i, int j)
    {
        int currentIndex = map2Dto1D(i, j);

        if (STATE[currentIndex]) {
            return;
        }

        STATE[currentIndex] = true;

        if (i == 1) {
            UF.union(currentIndex, 0);
        } else if (i > 1 && isOpen(i-1, j)) {
            UF.union(currentIndex, map2Dto1D(i-1, j));
        }

        if (i < N && isOpen(i+1, j)) {
            UF.union(currentIndex, map2Dto1D(i+1, j));
        }

        if (j > 1 && isOpen(i, j-1)) {
            UF.union(currentIndex, map2Dto1D(i, j-1));
        }

        if (j < N && isOpen(i, j+1)) {
            UF.union(currentIndex, map2Dto1D(i, j+1));
        }

        // Backwash fix
        if (i == N && isFull(i, j)) {
            UF.union(currentIndex, 1);
        }
        
        if (!percolates()) {
            for (int k = 1; k <= N; ++k) {
                if (isFull(N, k)) {
                    UF.union(currentIndex, 1);
                }
            }
        }
    }

    public boolean isOpen(int i, int j)
    {
        return STATE[map2Dto1D(i, j)];
    }

    public boolean isFull(int i, int j)
    {
        return UF.connected(map2Dto1D(i, j), 0);
    }

    public boolean percolates()
    {
        return UF.connected(0, 1);
    }

    private int map2Dto1D(int i, int j)
    {
        if (i < 1 || j < 1 || i > N || j > N) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        return (i-1)*N + (j-1) + 2;
    }
}
