//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//
//import edu.princeton.cs.algs4.StdIn;
//import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    
//    public static void main(String[] args) {
//        try {
//            System.setIn(new FileInputStream("/Users/clauderoy/Git/coursera-algorithms1/week1/percolation/tests/input6.txt"));
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        int n = StdIn.readInt();
//        int nbSitesToOpen = 50;
//        Percolation perc = new Percolation(n);
//        StdOut.println("new perc of : "+n);
//        int nbSites = 0;
//        while (nbSites < nbSitesToOpen && !StdIn.isEmpty()) {
//            int row = StdIn.readInt();
//            int col = StdIn.readInt();
//
//            perc.open(row, col);
//            StdOut.println((++nbSites)+" opened: "+row+", q: "+col);
//            
//        }
//        StdOut.println("percolates: "+perc.percolates());
//        StdOut.println("is full: "+perc.isFull(2, 1));
//        StdOut.println("2,1 root: "+perc.uf.find(6));
//        StdOut.println("top root: "+perc.uf.find(36));
//        //StdOut.println("the top is ocnnected to: "+perc.uf.find(perc.top));
//    }
    
    private final int top, n;
    private final WeightedQuickUnionUF uf;
    private final int[][] neighbors;
    private int nbOpenedSites;
    private int[] openedSites;
    
    
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("n must be greater than 0");
        this.n = n;
        this.uf = new WeightedQuickUnionUF(n*n+1); // Add 1 virtual site for the top
        this.openedSites = new int[n*n];
        
        top = n*n;
        neighbors = new int[][] {
            {-1,0}, // top
            {0,1},  // right
            {1,0},  // bot
            {0,-1}  // left
        };
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        
        int ind = getFlatIndex(row,col);
        
        // if not already opened
        if (openedSites[ind] == 0) {
            openedSites[ind] = 1;
            ++nbOpenedSites;
            if (row == 1) { // connect top row with virtual node
                uf.union(ind, top);
            }
            makeUnionWithOpenedNeighbors(row,col);
        }
        
    }


    private void makeUnionWithOpenedNeighbors(int row, int col) {
        
        for(int i = 0; i < neighbors.length;++i) {
            int neighborRow = row + neighbors[i][0];
            int neighborCol = col + neighbors[i][1];
            
            if (isRowColValid(neighborRow,neighborCol) && isOpen(neighborRow,neighborCol)) {
                int curIndex = getFlatIndex(row,col);
                int neighborIndex = getFlatIndex(neighborRow,neighborCol);
                uf.union(neighborIndex, curIndex);
            }
        }
        
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int ind = getFlatIndex(row,col);
        return openedSites[ind] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int ind = getFlatIndex(row,col);
        
        return isOpen(row,col) && uf.find(ind) == uf.find(top); // is connected to the top?
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return nbOpenedSites;
    }

    // does the system percolate?
    public boolean percolates() {
        
        for (int i = 1; i <= n; ++i) {
            if (this.isFull(n, i)) {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean isRowColValid(int row, int col) {
        return row >= 1 && row <= n && col >= 1 && col <= n;
    }

    
    private int getFlatIndex(int row, int col) {
        if (!isRowColValid(row,col))
            throw new IllegalArgumentException("row and column must be valid");
        return (row-1)*n + col - 1;
    }
}
