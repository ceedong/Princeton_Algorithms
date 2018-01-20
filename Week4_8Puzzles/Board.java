import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.lang.Math;
import java.util.Queue;
import java.util.LinkedList;
public class Board {
    private int[][] blocks;
    private int N;
    private int priorityNum; // which should be compared by the comparator
    public Board(int[][] blocks) {
        this.blocks = blocks; //constructor
        this.N = blocks.length;
        this.priorityNum = this.manhattan();
        // test for the constructor
        for (int i = 0; i < this.blocks.length; i++) {
            for (int j = 0; j < this.blocks[0].length; j++) {
                // StdOut.println(this.blocks[i][j]);
            }
        }
    }           // construct a board from an n-by-n array of blocks
                // (where blocks[i][j] = block in row i, column j)
    public int dimension() {
        return N;
    }                 // board dimension n
    public int hamming() {
        int count = 0; // to count for the number of blocks out of place
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                if (i == blocks.length -1 && j == blocks.length -1) break;
                else if (blocks[i][j] != (blocks.length * i + j + 1)) {
                count++;
                System.out.println(blocks[i][j] + "!!!");
                }
            }
        }
        // StdOut.println(count);
        return count;
    }                  
    public int manhattan() {
        int count = 0; // to record the manhattan distance
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                // we need to calculate the actual distance and the desired distance for the number
                int number = blocks[i][j];
                if (number == 0) continue;
                int row = (number % N == 0) ? number / N - 1 : number / N;
                // StdOut.println("The row for number  " + number + " is:" + row);
                int col = (number % N == 0) ? N - 1: number % N - 1;
                // StdOut.println("The col for number  " + number + " is:" + col);
                count += Math.abs(i - row) + Math.abs(j - col);
                // StdOut.println("The count for number  " + number + " is:" + Math.abs(i - row) + Math.abs(j - col));
                // StdOut.println("The current coordinates are " + i + ", " + j);
            }
        }
        // StdOut.println(count);
        
        return count;
    }             
    public boolean isGoal() {
        int count = 0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                int number = blocks[i][j];
                if (number == 0) continue;
                int row = (number % N == 0) ? number / N - 1 : number / N;
                int col = (number % N == 0) ? N - 1: number % N - 1;
                if (row != i || col != j) return false;
                count++;
            }
        }
        if (count == N * N -1) return true;
        else return false;
    }
    
    public Board twin() {
        int[][] matrix = new int[N][N];
        // get a new cloned matrix and operates on it .
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = blocks[i][j];
            }
        }
        // the assumption is that the matrix's length is greater than or equal to 2
        if (matrix[0][0] != 0 && matrix[0][1] != 0) swap(matrix, 0, 0, 0, 1);
        else swap(matrix, 1, 0, 1, 1);
        Board result = new Board(matrix);
        return result;
        
    }                    // a board that is obtained by exchanging any pair of blocks
    
    private void swap(int[][] matrix, int i, int j, int p, int q) {
        int temp = matrix[i][j];
        matrix[i][j] = matrix[p][q];
        matrix[p][q] = temp;
    }
    
    // The code imitates the one in the book on page 103, the right column.
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        Board that = (Board) y;
        if (this.N != that.N) return false;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.blocks[i][j] != that.blocks[i][j]) return false;
            }
        }
        return true;
    }        // does this board equal y?
    public Iterable<Board> neighbors() {
        Queue<Board> queue = new LinkedList<Board>();
        // there are four cases in total, so we just judge if the cases are valid
        // and if yes, then just add it on the queue
        int[][] matrix = new int[N][N];
        // first we need to find out the zero element in the matrix
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = blocks[i][j];
            }
        }
        int rowZero = 0;
        int colZero = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (matrix[i][j] == 0) {
                    rowZero = i;
                    colZero = j;
                    break;
                }
            }
        }
        // judge whether it is exchangeable
        if (rowZero + 1 < N && colZero < N) {
        int[][] temp1 = clone(matrix);
        swap(temp1, rowZero, colZero, rowZero + 1, colZero);
        queue.add(new Board(temp1));
        // StdOut.println("The first one is pass!");
        }
        if (rowZero - 1 >= 0 && colZero < N) {
        int[][] temp2 = clone(matrix);
        swap(temp2, rowZero, colZero, rowZero - 1, colZero);
        queue.add(new Board(temp2));
        // StdOut.println("The second one is pass!");
        }
        if (rowZero < N && colZero + 1 < N) {
        int[][] temp3 = clone(matrix);
        swap(temp3, rowZero, colZero, rowZero, colZero + 1);
        queue.add(new Board(temp3));
        // StdOut.println("The third one is pass!");
        }
        if (rowZero < N && colZero - 1 >= 0) {
        int[][] temp4 = clone(matrix);
        swap(temp4, rowZero, colZero, rowZero, colZero - 1);
        queue.add(new Board(temp4));
        // StdOut.println("The fourth one is pass!");
        }
        return queue;
    }     // all neighboring boards
    
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }               // string representation of this board (in the output format specified below)
    
    private int[][] clone(int[][] matrix) {
        int[][] result = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                result[i][j] = matrix[i][j];
            }
        }
        return result;
    }
    
    //setter and getter for the private number priorityNum:
    public int getPrior() {
        return this.priorityNum;
    }
    
    public void setPrior(int num) {
        this.priorityNum = num;
    }
    
    public static void main(String[] args) {    
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        // StdOut.println(initial.dimension());
        // StdOut.println(initial.hamming());
        // StdOut.println(initial.manhattan());
        // StdOut.println(initial.isGoal());
        Board result = initial;
        StdOut.println(initial.twin());
        // StdOut.println("-------");
        // StdOut.println(initial.equals(result));
        // StdOut.println(initial.neighbors());
        // StdOut.println(initial.toString());
    } // unit tests (not graded)
}
