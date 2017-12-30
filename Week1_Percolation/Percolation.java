/******************************************************************************
 *
 *  Compilation:  javac Percolation.java
 *  Execution:    java Percolation
 *  Dependencies: StdDraw.java StdOut.java
 *
 *  Implements percolation grid
 *
 ******************************************************************************/
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdIn;
public class Percolation {
    /**
     * @param matrix to record whether the site is open or not
     * @param value to indicate total number of points
     * @param openSites indicates the number of open sites
     * @param a is an object of weightedquickunionuf class
     */
    private boolean[] matrix;
    private final int value;
    private int openSites;
    private final WeightedQuickUnionUF a;
    private final WeightedQuickUnionUF backwash;
    private final int topIndex;
    private final int bottomIndex;
    /**
     * @code constructor
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        value = n; // the variable is used to record the total col/row of the matrix
        matrix = new boolean[n*n + 2];
        topIndex = 0;
        bottomIndex = n*n + 1;
        matrix[topIndex] = true;
        matrix[bottomIndex] = true;
        a = new WeightedQuickUnionUF(n*n + 2);
        backwash = new WeightedQuickUnionUF(n*n +1);
        //        for(int j = 1; j <= value; j++) {
        //            int i = 1;
        //            int topSitesIndex = getNumber(i, j);
        //            a.union(topIndex, topSitesIndex);
        //            i = value;
        //            int bottomSitesIndex = getNumber(i, j);
        //            a.union(bottomIndex, bottomSitesIndex);
        //        }
        openSites = 0;
    }
    /**
     * open one of the sites
     */
    public void open(int row, int col) {
        isValidInput(row, col);
        int number = getNumber(row, col);
        if (!matrix[number]) {
            matrix[number] = true;
            openSites++; }
        if (matrix[number]) {
            if (row == 1) {
                a.union(number, topIndex);
                backwash.union(number, topIndex);
            }
            if (row == value) {
                a.union(number, bottomIndex);
            }
            if (row > 1) {
                if (isOpen(row -1, col)) {
                    a.union(number -value, number);
                    backwash.union(number -value, number);
                }
            }
            if (row < value) {
                if (isOpen(row +1, col)) {
                    a.union(number +value, number);
                    backwash.union(number +value, number);
                }
            }
            if (col > 1) {
                if (isOpen(row, col -1)) {
                    a.union(number, number -1);
                    backwash.union(number, number -1);
                }
            }
            if (col < value) {
                if (isOpen(row, col +1)) {
                    a.union(number, number +1);
                    backwash.union(number, number +1);
                }
            }
        }
        
        
    }
    /**
     * @return whether it is open or not
     */
    public boolean isOpen(int row, int col) {
        isValidInput(row, col);
        int number = getNumber(row, col);
        return matrix[number];
    }
    /**
     * @return whether given point is full or not
     */
    public boolean isFull(int row, int col) {
        isValidInput(row, col);
        if (!isOpen(row, col)) {
            return false;
        }
        int number = getNumber(row, col);
        return backwash.connected(topIndex, number);
    }
    /**
     * @return the number of sites
     */
    public int numberOfOpenSites() {
        return openSites;
    }      // number of open sites
    /**
     * @return whether the matrix percolates or not
     */
    public boolean percolates() {
        return a.connected(topIndex, bottomIndex);
    }
    /**
     * @code helper method to indicate whether the input param is valid
     * or not
     */
    private void isValidInput(int row, int col) {
        if (row < 1 || row > value || col < 1 || col > value) {
            throw new IllegalArgumentException("Please give some meaningful input!");
        }
    }
    /**
     * @return a number of the site
     */
    private int getNumber(int row, int col) {
        return (row -1)*value + col;
    }
    /**
     * @code testing purpose
     */
    public static void main(String[] args) {
        int n = StdIn.readInt();
        Percolation pe = new Percolation(n);
        pe.open(1, 1);
        pe.open(2, 2);
        System.out.println(pe.percolates());
        System.out.println(pe.numberOfOpenSites());
        System.out.println(pe.isFull(2, 1));
    }   // test client (optional)
}
