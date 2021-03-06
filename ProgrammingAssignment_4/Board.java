public class Board {
    private int[][] a;
    private int N;

    public Board(int[][] blocks) {
        if (blocks == null)
            throw new java.lang.NullPointerException();

        N = blocks.length;
        if (N == 0 || N != blocks[0].length)
            throw new java.lang.IllegalArgumentException();

        a = new int[N][N];

        for (int i = 0; i < N; ++i)
            for (int j = 0; j < N; ++j)
                a[i][j] = blocks[i][j];
    }

    public int dimension() {
        return N;
    }

    public int hamming() {
        int wrongCount = 0;

        for (int i = 0; i < N; ++i)
            for (int j = 0; j < N; ++j) {
                if (a[i][j] == 0)
                    continue;

                if (a[i][j] != i*N + j + 1)
                    wrongCount++;
            }

        return wrongCount;
    }

    public int manhattan() {
        int distance = 0;

        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                if (a[i][j] == 0)
                    continue;

                int righti = (a[i][j] - 1) / N; 
                int rightj = a[i][j] - 1 - righti * N;

                distance += java.lang.Math.abs(i - righti) + java.lang.Math.abs(j - rightj);
            }
        }

        return distance;
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    public Board twin() {
        int[][] b = new int[N][N];

        int rand_i, rand_j;

        for (int i = 0; i < N; ++i)
            for (int j = 0; j < N; ++j)
                b[i][j] = this.a[i][j];

        do {
            rand_i = StdRandom.uniform(N);
            rand_j = StdRandom.uniform(N-1);
        } while (b[rand_i][rand_j] == 0 || b[rand_i][rand_j+1] == 0);


        int tmp = b[rand_i][rand_j];
        b[rand_i][rand_j] = b[rand_i][rand_j+1];
        b[rand_i][rand_j+1] = tmp;

        return new Board(b);
    }

    public boolean equals(Object y) {
        if (y == this)
            return true;

        if (y == null)
            return false;

        if (y.getClass() != this.getClass())
            return false;

        Board that = (Board) y;

        if (this.N != that.N)
            return false;

        for (int i = 0; i < N; ++i)
            for (int j = 0; j < N; ++j)
                if (this.a[i][j] != that.a[i][j])
                    return false;


        return true;
    }

    private int[][] copyBlocks() {
        int b[][] = new int[N][N];
        for (int i = 0; i < N; ++i)
            for (int j = 0; j < N; ++j)
                b[i][j] = this.a[i][j];

        return b;
    }

    public Iterable<Board> neighbors() {
        Bag<Board> bag = new Bag<Board>();
        int zero_i = 0;
        int zero_j = 0;

        outerloop:
        for (int i = 0; i < N; ++i)
            for (int j = 0; j < N; ++j)
                if (a[i][j] == 0) {
                    zero_i = i;
                    zero_j = j;
                    break outerloop;
                }

        if (zero_i > 0) {
            int b[][] = this.copyBlocks();
            b[zero_i][zero_j] = b[zero_i-1][zero_j];
            b[zero_i-1][zero_j] = 0;
            bag.add(new Board(b));
        }

        if (zero_i < N-1) {
            int b[][] = this.copyBlocks();
            b[zero_i][zero_j] = b[zero_i+1][zero_j];
            b[zero_i+1][zero_j] = 0;
            bag.add(new Board(b));
        }

        if (zero_j > 0) {
            int b[][] = this.copyBlocks();
            b[zero_i][zero_j] = b[zero_i][zero_j-1];
            b[zero_i][zero_j-1] = 0;
            bag.add(new Board(b));
        }

        if (zero_j < N-1) {
            int b[][] = this.copyBlocks();
            b[zero_i][zero_j] = b[zero_i][zero_j+1];
            b[zero_i][zero_j+1] = 0;
            bag.add(new Board(b));
        }

        return bag;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", a[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        int b[][] = {
                {0, 2,  3, 4},
                {5,  6,  7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 1}
        };

        Board board = new Board(b);
        System.out.println("Hamming: " + board.hamming());
        System.out.println("Manhatten: " + board.manhattan());
        System.out.println("----------------");
        System.out.println(board);

        /*
        Board twin = board.twin();
        System.out.println(twin);

        System.out.println(board.equals(board));
        */

        System.out.println("Neighbors:");
        for (Board neighbor : board.neighbors()) {
            System.out.println("Hamming: " + neighbor.hamming());
            System.out.println("Manhatten: " + neighbor.manhattan());
            System.out.println("----------------");
            System.out.println(neighbor);
        }
    }
}
