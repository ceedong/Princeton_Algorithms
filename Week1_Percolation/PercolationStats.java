/******************************************************************************
 *
 *  Compilation:  javac Percolation.java
 *  Execution:    java Percolation
 *  Dependencies: StdDraw.java StdOut.java
 *
 *  Implements percolation grid
 *
 ******************************************************************************/
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;
public class PercolationStats {
    private int totalNum;
    private double percent;
    private double[] results;
    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Please give meaningful inputs.");
        }
        totalNum = n*n;
        results = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation pe = new Percolation(n);
            int openSites = 0;
            while (!pe.percolates()) {
                int row = StdRandom.uniform(1, n+1);
                int col = StdRandom.uniform(1, n+1);
                if (!pe.isOpen(row, col)) {
                    pe.open(row, col);
                    openSites++;
                }
            }
            percent = (double) openSites/(double) totalNum;
            results[i] = percent;
        }
        mean = StdStats.mean(results);
        stddev = StdStats.stddev(results);
        confidenceLo = mean - 1.96 * stddev() / Math.sqrt(results.length);
        confidenceHi = mean + 1.96 * stddev() / Math.sqrt(results.length);
    }
    public double mean() {
        return mean;
    }
    public double stddev() {
        return stddev;
    }
    public double confidenceLo() {
        return confidenceLo;
    }
    public double confidenceHi() {
        return confidenceHi;
    }
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval = "
                       + stats.confidenceLo() + ", "
                       + stats.confidenceHi());
    }
}
