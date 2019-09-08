package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private PercolationFactory perMaker;
    private int dimension;
    private int testNums;
    private double[] thresholds;
    private double mean;
    private double sum;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (T <= 0 || N <= 0) {
            throw new IllegalArgumentException();
        }
        Percolation temp;
        testNums = T;
        perMaker = pf;
        dimension = N;
        thresholds = new double[T];
        double threshold;

        for (int i = 0; i < testNums; i++) {
            temp = perMaker.make(dimension);
            while (!temp.percolates()) {
                temp.open(StdRandom.uniform(dimension), StdRandom.uniform(dimension));
            }
            threshold = (double) temp.numberOfOpenSites() / (double) (dimension * dimension);
            thresholds[i] = threshold;
            sum = sum + threshold;
        }

    }

    public double mean() {

        mean = sum / testNums;
        return mean;
    }

    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    public double confidenceLow() {
        return (mean() - (1.96 * stddev()) / Math.sqrt(testNums));
    }

    public double confidenceHigh() {
        return (mean() + (1.96 * stddev()) / Math.sqrt(testNums));
    }

}
