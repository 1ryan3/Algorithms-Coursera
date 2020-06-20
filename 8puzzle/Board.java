import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Board {

    private final int n;
    private int[] board;


    public Board(int[][] tiles) {
        n = tiles.length;
        board = new int[n * n];
        for (int x = 0; x < board.length; x++) {
            board[x] = tiles[x / n][x % n];
        }
    }

    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append(n + "\n");
        for (int x = 0; x < n * n; x++) {
            output.append(String.format("%5d ", board[x]));
            if (x % n == n - 1) {
                output.append("\n");
            }
        }
        return output.toString();
    }

    public int dimension() {
        return n;
    }

    public int hamming() {
        int ham = 0;
        for (int x = 0; x < n * n; x++) {
            if (board[x] != x + 1 && board[x] != 0) ham++;
        }
        return ham;
    }

    public int manhattan() {
        int man = 0;


        for (int i = 0; i < n * n; i++) {

            if (board[i] != i + 1 && board[i] != 0) {
                int rowBoard = i / n;
                int colBoard = i % n;
                int rowActual = (board[i] - 1) / n;
                int colActual = (board[i] - 1) % n;
                man += Math.abs(rowBoard - rowActual) + Math.abs(colBoard - colActual);
                // StdOut.println(man);
            }
        }
        return man;
    }

    public boolean isGoal() {

        if (board[(n * n) - 1] != 0) return false;

        for (int x = 0; x < n * n; x++) {
            if (board[x] != x + 1 && board[x] != 0) return false;
        }
        return true;
    }

    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board other = (Board) y;
        if (other.n != this.n) return false;
        return Arrays.equals(other.board, this.board);
    }

    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<>();
        int blank = 0;
        Board temp;
        int[][] twinBoard = new int[n][n];
        for (int i = 0; i < n * n; i++) {
            twinBoard[i / n][i % n] = this.board[i];
        }
        for (int x = 0; x < n * n; x++) {
            if (board[x] == 0) {
                blank = x;
                break;
            }
        }

        if (blank % n < n - 1) { // right swap
            temp = new Board(twinBoard);
            temp.board[blank] = temp.board[blank + 1];
            temp.board[blank + 1] = 0;
            neighbors.push(temp);
        }
        if (blank / n > 0) { // up swap
            temp = new Board(twinBoard);
            temp.board[blank] = temp.board[blank - n];
            temp.board[blank - n] = 0;
            neighbors.push(temp);
        }
        if (blank % n > 0) { // left swap
            temp = new Board(twinBoard);
            temp.board[blank] = temp.board[blank - 1];
            temp.board[blank - 1] = 0;
            neighbors.push(temp);
        }
        if (blank / n < n - 1) { // down swap
            temp = new Board(twinBoard);
            temp.board[blank] = temp.board[blank + n];
            temp.board[blank + n] = 0;
            neighbors.push(temp);
        }
        return neighbors;
    }

    public Board twin() {

        int[][] twinBoard = new int[n][n];

        for (int i = 0; i < n * n; i++) {
            twinBoard[i / n][i % n] = this.board[i];
        }

        Board twin = new Board(twinBoard);
        for (int x = 0; x < n * n; x++) {
            if (twin.board[x] != 0 && twin.board[x + 1] != 0) {
                int temp = twin.board[x];
                twin.board[x] = twin.board[x + 1];
                twin.board[x + 1] = temp;
                break;
            }
        }
        return twin;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board testBoard = new Board(tiles);

        StdOut.println(testBoard.toString());
        StdOut.println(testBoard.hamming());
        StdOut.println(testBoard.manhattan());


        StdOut.println(testBoard.twin().toString());

        Iterable<Board> temp = testBoard.neighbors();

        for (Board x : temp) {
            StdOut.println(x.toString());
        }
    }
}
