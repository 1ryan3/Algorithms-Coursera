import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE95 = 1.96;
    private final double[] thresholdValues;
    private final int trialCount;
    private double sampleMean;
    private double sampleStdDev;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("Grid size and trial # must both be larger than 0!");
        thresholdValues = new double[trials];
        trialCount = trials;
        double openCount = 0;
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);

                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                    openCount += 1;
                }
            }
            thresholdValues[i] = openCount / (n * n);
            openCount = 0;

        }
        sampleMean = StdStats.mean(thresholdValues);
        sampleStdDev = StdStats.stddev(thresholdValues);
    }

    // sample mean of percolation threshold
    public double mean() {
        return sampleMean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return sampleStdDev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return sampleMean - ((CONFIDENCE95 * sampleStdDev) / Math.sqrt(trialCount));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return sampleMean + ((CONFIDENCE95 * sampleStdDev) / Math.sqrt(trialCount));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats trial = new PercolationStats(n, t);
        System.out.println("mean = " + trial.mean());
        System.out.println("stddev = " + trial.stddev());
        System.out.println(
                "95% confidence interval = [" + trial.confidenceLo() + ", " + trial.confidenceHi()
                        + "]");
    }

}
