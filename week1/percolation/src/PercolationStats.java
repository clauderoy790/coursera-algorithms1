import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private final int n, trials;
    private final double[] tresholds;
    private double lowConfidence, highConfidence, deviation, mean, percolationTreshold = 0;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials must be both greater than 0.");
        }
        
        this.n = n;
        this.trials = trials;
        this.tresholds = new double[trials];
        simulate();
        
    }

    private void simulate() {
        
        for(int i = 0; i < trials; ++i) {
            Percolation perc = new Percolation(n);
            
            // repeat until system percolates
            while (!perc.percolates()) {
                int rndRow = StdRandom.uniform(1,n+1);
                int rndCol = StdRandom.uniform(1,n+1);
                perc.open(rndRow, rndCol);
            }
            tresholds[i] = perc.numberOfOpenSites() / (double)(n*n);
            percolationTreshold += tresholds[i];
        }
        percolationTreshold /= trials;
        this.deviation = StdStats.stddev(tresholds);
        this.mean = StdStats.mean(tresholds);
        calculateConfidences();
        
    }

    private void calculateConfidences() {
        double val = ((1.96d*this.deviation)/Math.sqrt(trials));
        this.lowConfidence = percolationTreshold - val;
        this.highConfidence = percolationTreshold + val;
        
    }

    // sample mean of percolation threshold
    public double mean() {
        return this.mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return this.deviation;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return lowConfidence;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return highConfidence;
    }

   // test client (see below)
   public static void main(String[] args) {
       args = new String[] {"2","10000"};
       int n = Integer.parseInt(args[0]);
       int T = Integer.parseInt(args[1]);
       PercolationStats stats = new PercolationStats(n,T);
      
       StdOut.println("mean\t\t\t = "+stats.mean());
       StdOut.println("stddev\t\t\t = "+stats.stddev());
       StdOut.println("95% confidence interval\t = ["+stats.confidenceLo()+", "+stats.confidenceHi()+"]");
   }

}