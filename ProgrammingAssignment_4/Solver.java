import java.util.Comparator;

public class Solver {
    Queue<Board> solution;
    boolean isSolvable;

    public Solver(Board initial) {
        isSolvable = true;

        solution = new Queue<Board>();

        MinPQ<PuzzleNode> queue = new MinPQ<PuzzleNode>(1, new PuzzleNodeComparator());
        queue.insert(new PuzzleNode(initial, 0, null));

        do {
//            System.out.println("\n-----------------");
//            for (PuzzleNode n : queue)
//                System.out.println(n);

            PuzzleNode node = queue.delMin();
            while (! queue.isEmpty())
                queue.delMin();
//            System.out.println("Min:");
//            System.out.println(node);

            Board b = node.board();

            solution.enqueue(b);
            int moves = node.moves();

            if (b.isGoal())
                break;

            for (Board neighbor : b.neighbors())
                if (node.prevNode() == null || ! neighbor.equals(node.prevNode().board()))
                    queue.insert(new PuzzleNode(neighbor, moves+1, node));
        } while (queue.size() > 0);
    }

    public int moves() {
        return solution.size() - 1;
    }

    public Iterable<Board> solution() {
        return solution;
    }

    public boolean isSolvable() {
        return isSolvable;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    private static class PuzzleNode {
        Board board;
        int moves;
        PuzzleNode prevNode;

        PuzzleNode(Board board, int moves, PuzzleNode prevNode) {
            this.board = board;
            this.moves = moves;
            this.prevNode = prevNode;
        }

        int moves() { return moves; }
        Board board() { return board; }
        PuzzleNode prevNode() { return prevNode; }

        public String toString() {
            StringBuffer buffer = new StringBuffer();

            buffer.append("Moves: " + this.moves + "\n");
            buffer.append("Hamming: " + this.board.hamming() + "\n");
            buffer.append("Manhattan: " + this.board.manhattan() + "\n");
            buffer.append(this.board.toString());

            return buffer.toString();
        }
    }

    private static class PuzzleNodeComparator implements Comparator<PuzzleNode> {
        public int compare(PuzzleNode v, PuzzleNode w) {
            if (v == null || w == null)
                throw new java.lang.NullPointerException();

            if (v == w)
                return 0;

            int vS = v.board().hamming() + v.board().manhattan() + v.moves();
            int wS = w.board().hamming() + w.board().manhattan() + w.moves();

            if      (vS == wS)  return 0;
            else if (vS < wS)   return -1;
            else                return 1;
        }
    }
}
