import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {

    private int minMoves;
    private SearchNode solution;
    private boolean solvable;

    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final int moves;
        private final SearchNode prev;
        private final int priority;

        public SearchNode(Board b, int m, SearchNode p) {
            board = b;
            moves = m;
            prev = p;
            priority = m + b.manhattan();
        }

        public int compareTo(SearchNode that) {
            if (that == null) throw new NullPointerException();
            else return Integer.compare(this.priority, that.priority);
        }

        public Comparator<SearchNode> priorityOrder() {
            return new PriorityOrder();
        }

        private class PriorityOrder implements Comparator<SearchNode> {
            public int compare(SearchNode s1, SearchNode s2) {
                if (s1 == null || s2 == null) throw new NullPointerException();
                return Integer.compare(s1.priority, s2.priority);
            }
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        if (initial == null) throw new IllegalArgumentException();

        MinPQ<SearchNode> boardQ = new MinPQ<>();
        MinPQ<SearchNode> twinBoardQ = new MinPQ<>();
        SearchNode primary = new SearchNode(initial, 0, null);
        SearchNode twin = new SearchNode(initial.twin(), 0, null);
        boardQ.insert(primary);
        twinBoardQ.insert(twin);
        solvable = true;

        minMoves = initial.manhattan();

        while (!primary.board.isGoal()) {
            if (twin.board.isGoal()) {
                solvable = false;
                return;
            }
            primary = boardQ.delMin();
            twin = twinBoardQ.delMin();
            for (Board x : primary.board.neighbors()) {
                if (primary.prev != null) {
                    if (!x.equals(primary.prev.board)) {
                        boardQ.insert(new SearchNode(x, primary.moves + 1, primary));
                    }
                }
                else {
                    boardQ.insert(new SearchNode(x, primary.moves + 1, primary));
                }
            }
            for (Board y : twin.board.neighbors()) {
                if (twin.prev != null) {
                    if (!y.equals(twin.prev.board)) {
                        twinBoardQ.insert(new SearchNode(y, twin.moves + 1, twin));
                    }
                }
                else {
                    twinBoardQ.insert(new SearchNode(y, twin.moves + 1, twin));
                }
            }
        }
        solution = primary;
        minMoves = primary.moves;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board
    public int moves() {
        if (!solvable) return -1;
        return minMoves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if (!solvable) return null;
        Stack<Board> out = new Stack<>();
        SearchNode temp = solution;
        while (temp.prev != null) {
            out.push(temp.board);
            temp = temp.prev;
        }
        out.push(temp.board);
        return out;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);
        // StdOut.println(solver.moves());
        // StdOut.println(solver.isSolvable());
        for (Board x : solver.solution())
            StdOut.println(x.toString());
    }
}
