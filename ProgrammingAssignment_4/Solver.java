import java.util.Comparator;

public class Solver {
    Stack<Board> solution;
    boolean isSolvable;

    public Solver(Board initial) {
        isSolvable = true;
        solution = new Stack<Board>();

        MinPQ<PuzzleNode> queue = new MinPQ<PuzzleNode>(1, new PuzzleNodeComparator());
        queue.insert(new PuzzleNode(initial, 0, null));

        MinPQ<PuzzleNode> twinQueue = new MinPQ<PuzzleNode>(1, new PuzzleNodeComparator());
        twinQueue.insert(new PuzzleNode(initial.twin(), 0, null));

        Bag<MinPQ<PuzzleNode>> queueBag = new Bag<MinPQ<PuzzleNode>>();
        queueBag.add(queue);
        queueBag.add(twinQueue);

        PuzzleNode node;

OUTER:
        while (true) {
            for (MinPQ<PuzzleNode> q : queueBag) {
                node    = q.delMin();
                Board b = node.board();

                if (b.isGoal())
                    break OUTER;

                for (Board neighbor : b.neighbors())
                    if (node.prevNode() == null || ! neighbor.equals(node.prevNode().board()))
                        q.insert(new PuzzleNode(neighbor, node.moves()+1, node));
            }
        }

        do {
            solution.push(node.board());
            node = node.prevNode();
        } while (node != null);

        if (! solution.peek().equals(initial))
            isSolvable = false;
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
        int priority() { return board.manhattan() + moves; }
    }

    private static class PuzzleNodeComparator implements Comparator<PuzzleNode> {
        public int compare(PuzzleNode v, PuzzleNode w) {
            if (v == null || w == null)
                throw new java.lang.NullPointerException();

            if (v == w)
                return 0;

            int vP = v.priority();
            int wP = w.priority();

            if      (vP < wP)
                return -1;
            else if (vP > wP)
                return 1;
            else if (v.board.hamming() < w.board.hamming())
                return -1;
            else if (v.board.hamming() > w.board.hamming())
                return 1;
            else
                return 0;
        }
    }
}
