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
        this.records = new double[trials];
        for (int num = 0; num < trials; num++) {
            this.grids = new Percolation(n);
            int openSites = 0;
            while (!this.grids.percolates()) {
                int i = StdRandom.uniform(1, n + 1);
                int j = StdRandom.uniform(1, n + 1);
                if (!this.grids.isOpen(i, j)) {
                    this.grids.open(i, j);
                    openSites++;
                }
            }
            this.records[num] = (double) openSites / (n * n);
        }
    }
    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(this.records);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(this.records);

    }
    // low  endpoint of 95% confidence interval
    public double confidenceLo()  {
        return mean() - (1.96 * stddev() / Math.sqrt(this.records.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi()  {
        return mean() + (1.96 * stddev() / Math.sqrt(this.records.length));
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