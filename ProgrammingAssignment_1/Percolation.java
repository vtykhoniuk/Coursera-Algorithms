public class Percolation {
    private WeightedQuickUnionUF UF;
    private byte[] STATE;
    private int N = 0;
    private boolean PERCOLATES = false;

    public Percolation(int N)
    {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        this.N = N;

        STATE = new byte[N*N+1];
        UF = new WeightedQuickUnionUF(STATE.length);

        // Two virtual node (upper) that is always openned
        STATE[0] = 1;

        for (int i = STATE.length - 1; i >= STATE.length - N; --i) {
            STATE[i] = 2;
        }
    }

    private void union(int i, int j)
    {
        byte tmp = (byte) (STATE[UF.find(i)] | STATE[UF.find(j)]);
        UF.union(i, j);
        STATE[UF.find(i)] = (byte) (STATE[UF.find(i)] | tmp);
    }

    public void open(int i, int j)
    {
        int currentIndex = map2Dto1D(i, j);

        if ((STATE[currentIndex] & 1) == 1) {
            return;
        }

        STATE[currentIndex] = (byte) (STATE[currentIndex] ^ 1);

        if (i == 1) {
            union(currentIndex, 0);
        } else if (i > 1 && isOpen(i-1, j)) {
            union(currentIndex, map2Dto1D(i-1, j));
        }

        if (i < N && isOpen(i+1, j)) {
            union(currentIndex, map2Dto1D(i+1, j));
        }

        if (j > 1 && isOpen(i, j-1)) {
            union(currentIndex, map2Dto1D(i, j-1));
        }

        if (j < N && isOpen(i, j+1)) {
            union(currentIndex, map2Dto1D(i, j+1));
        }

        if (UF.connected(currentIndex, 0)
            && !PERCOLATES
            && STATE[UF.find(currentIndex)] == 3) {
            PERCOLATES = true;
        }
    }

    public boolean isOpen(int i, int j)
    {
        return (STATE[map2Dto1D(i, j)] & 1) == 1;
    }

    public boolean isFull(int i, int j)
    {
        return UF.connected(map2Dto1D(i, j), 0);
    }

    public boolean percolates()
    {
        return PERCOLATES;
    }

    private int map2Dto1D(int i, int j)
    {
        if (i < 1 || j < 1 || i > N || j > N) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        return (i-1)*N + (j-1) + 1;
    }
}
