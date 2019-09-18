/* *****************************************************************************
 *  Name:Rorschach Lin
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] thresholds;
    private final int trials;
    private final double confidence_95 = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.thresholds = new double[trials];
        this.trials = trials;
        for (int i = 0; i < trials; i++) { //
            int thresholdCounter = 0;
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int randomRow = StdRandom.uniform(1, n + 1);
                int randomCol = StdRandom.uniform(1, n + 1);
                percolation.open(randomRow, randomCol);
                thresholdCounter = percolation.numberOfOpenSites();
            }
            thresholds[i] = (double) thresholdCounter / (n * n);
        }
        double mean = this.mean();
        double stddev = this.stddev();
        double[] intervals = {
                this.confidenceLo(), this.confidenceHi()
        };
        // System.out.println("mean                    = " + mean);
        // System.out.println("stddev                  = " + stddev);
        // System.out
        //         .println("95% confidence interval = [" + intervals[0] + ", " + intervals[1] + "]");
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - confidence_95 * stddev() / Math.sqrt(trials);
    }

    // high endpoint osf 95% confidence interval
    public double confidenceHi() {
        return mean() + confidence_95 * stddev() / Math.sqrt(trials);
    }


    // test client (see below)
    public static void main(String[] args) {
        int n = 0;
        int t = 0;
        if (args.length == 2) {
            n = Integer.parseInt(args[0]);
            t = Integer.parseInt(args[1]);
        }
        PercolationStats percolationStats = new PercolationStats(n, t);
        // ----------------------------
        // double[] thresholds = new double[t];
        // for (int i = 0; i < t; i++) { //
        //     int thresholdCounter = 0;
        //     Percolation percolation = new Percolation(n);
        //     while (!percolation.percolates()) {
        //         int randomRow = StdRandom.uniform(1, n + 1);
        //         int randomCol = StdRandom.uniform(1, n + 1);
        //         percolation.open(randomRow, randomCol);
        //         thresholdCounter = percolation.numberOfOpenSites();
        //     }
        //     thresholds[i] = (double) thresholdCounter / (n * n);
        //     System.out.println(thresholds[i]);
        // }
        // //percolationStats.setThresholds(thresholds);
        // double mean = percolationStats.mean(thresholds);
        // double stddev = percolationStats.stddev(thresholds);
        // double[] intervals = {
        //         percolationStats.confidenceLo(thresholds), percolationStats.confidenceHi(thresholds)
        // };
        // System.out.println("mean                    = " + mean);
        // System.out.println("stddev                  = " + stddev);
        // System.out
        //         .println("95% confidence interval = [" + intervals[0] + ", " + intervals[1] + "]");
        //--------------------
    }

}
