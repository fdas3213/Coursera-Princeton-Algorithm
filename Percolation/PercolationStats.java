import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import java.lang.Math;
import java.lang.Integer;

public class PercolationStats {
    private Percolation grids;
    private double[] records;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials){
        if (n <= 0 || trials <= 0){
            throw new IllegalArgumentException();
        }
        records = new double[trials];
        for (int num = 0; num < trials; num++) {
            grids = new Percolation(n);
            int openSites = 0;
            while (!grids.percolates()) {
                int i = StdRandom.uniform(1, n + 1);
                int j = StdRandom.uniform(1, n + 1);
                if (!grids.isOpen(i, j)) {
                    grids.open(i, j);
                    openSites++;
                }
            }
            records[num] = (double) openSites / (n * n);
        }
    }
    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(records);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(records);

    }
    // low  endpoint of 95% confidence interval
    public double confidenceLo()  {
        return mean() - (1.96 * stddev() / Math.sqrt(records.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi()  {
        return mean() + (1.96 * stddev() / Math.sqrt(records.length));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats s = new PercolationStats(n,T);

        String conf = "[" + s.confidenceLo() + ", " + s.confidenceHi() + "]";
        StdOut.println("mean                   = " + s.mean());
        StdOut.println("stddev                 = " + s.stddev());
        StdOut.println("95% confidence interval = " + conf);

    }
}