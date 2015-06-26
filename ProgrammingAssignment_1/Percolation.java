public class Percolation {
    private WeightedQuickUnionUF m_uf;
    private boolean[] m_state;
    private int N;

    public Percolation(int N)               // create N-by-N grid, with all sites blocked
    {
        this.N = N;

        m_state = new boolean[N*N+2];
        m_uf = new WeightedQuickUnionUF(m_state.length);

        // Two virtual nodes (upper and bottom) that are always openned
        m_state[0] = true;
        m_state[1] = true;
    }

    public void open(int i, int j)          // open site (row i, column j) if it is not open already
    {
        int current_1d_index = map2Dto1D(i, j);

        if (m_state[current_1d_index] == true) {
            return;
        }

        m_state[current_1d_index] = true;

        if (i == 1) {
            m_uf.union(current_1d_index, 0);
        } else if (i > 1 && isOpen(i-1, j)) {
            m_uf.union(current_1d_index, map2Dto1D(i-1, j));
        }

        if (i < N && isOpen(i+1, j)) {
            m_uf.union(current_1d_index, map2Dto1D(i+1, j));
        }

        if (j > 1 && isOpen(i, j-1)) {
            m_uf.union(current_1d_index, map2Dto1D(i, j-1));
        }

        if (j < N && isOpen(i, j+1)) {
            m_uf.union(current_1d_index, map2Dto1D(i, j+1));
        }

        // Backwash fix
        if (i == N && isFull(i, j)) {
            m_uf.union(current_1d_index, 1);
        }
    }

    public boolean isOpen(int i, int j)     // is site (row i, column j) open?
    {
        return m_state[map2Dto1D(i, j)];
    }

    public boolean isFull(int i, int j)     // is site (row i, column j) full?
    {
        return m_uf.connected(map2Dto1D(i, j), 0);
    }

    public boolean percolates()             // does the system percolate?
    {
        return m_uf.connected(0, 1);
    }

    private int map2Dto1D(int i, int j)     // Maps two-dementional coordinates to 1-dementional
    {
        if (i < 1 || j < 1 || i > N || j > N) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        return (i-1)*N + (j-1) + 2;
    }
}
