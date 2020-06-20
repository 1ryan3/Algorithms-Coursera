import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] siteStatus;
    private final int gridSize;
    private int openSites;


    private final WeightedQuickUnionUF grid;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("Grid size must be greater than zero!");
        grid = new WeightedQuickUnionUF((n * n) + 2);
        siteStatus = new boolean[n + 1][n + 1];
        gridSize = n;
        openSites = 0;
        for (int r = 1; r <= n; r++) {
            for (int c = 1; c <= n; c++)
                siteStatus[r][c] = false;
        }
    }

    public void open(int row, int col) {
        if (validate(row, col)) {
            if (!isOpen(row, col)) {
                siteStatus[row][col] = true; // set to open

                if (validate(row - 1, col) && isOpen(row - 1, col)) // UP
                    grid.union(xyTo1D(row, col), xyTo1D(row - 1, col));

                if (validate(row, col + 1) && isOpen(row, col + 1)) // RIGHT
                    grid.union(xyTo1D(row, col), xyTo1D(row, col + 1));

                if (validate(row + 1, col) && isOpen(row + 1, col)) // DOWN
                    grid.union(xyTo1D(row, col), xyTo1D(row + 1, col));

                if (validate(row, col - 1) && isOpen(row, col - 1)) // LEFT
                    grid.union(xyTo1D(row, col), xyTo1D(row, col - 1));

                if (row == 1)
                    grid.union(xyTo1D(row, col), gridSize * gridSize);

                if (row == gridSize)
                    grid.union(xyTo1D(row, col), (gridSize * gridSize) + 1);
                openSites += 1;
            }
        }
        else
            throw new IllegalArgumentException("Out of grid bounds!");
    }

    public boolean isOpen(int row, int col) {
        if (!validate(row, col))
            throw new IllegalArgumentException("Not within grid bounds!");
        else if (siteStatus[row][col])
            return true;
        return false;
    }

    public boolean isFull(int row, int col) {
        if (!validate(row, col))
            throw new IllegalArgumentException("Not within grid bounds!");
        if (!siteStatus[row][col])
            return false;
        else if (grid.find(gridSize * gridSize) == grid.find(xyTo1D(row, col)))
            return true;
        return false;
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        if (grid.find(gridSize * gridSize) == grid.find((gridSize * gridSize) + 1))
            return true;
        return false;
    }

    private int xyTo1D(int row, int col) {
        return (row - 1) * gridSize + col - 1;
    }

    private boolean validate(int row, int col) {
        if (row <= 0 || col <= 0 || row > gridSize || col > gridSize)
            return false;
        return true;
    }


    public static void main(String[] args) {
        // for test use
    }
}
