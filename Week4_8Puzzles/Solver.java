import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.Comparator;
import java.util.Queue;
import java.util.LinkedList;

public class Solver {
    
    private int N;
    private Iterable<Board> result;
    private boolean isSolvable;
    public Solver(Board initial) {
        
    if (initial == null) throw new java.lang.IllegalArgumentException();  
    
    MinPQ<Board> pq = new MinPQ<Board>(new solverComparator());
    MinPQ<Board> pq_twin = new MinPQ<Board>(new solverComparator());
    
    Queue<Board> queue = new LinkedList<Board>();
    Queue<Board> queue_twin = new LinkedList<Board>();
    
    pq.insert(initial);
    pq_twin.insert(initial.twin());
    
    int step = 0;
    
    while (!pq.min().isGoal() && !pq_twin.min().isGoal()) {
        step++;
        // StdOut.println("Current step is " + step);
        // test-case : to detect why the solution is not optimal, I choose to comment all the 
        // lines related to the queue itself and try to see whether the corresponding twin got
        // the answer or not.
        Board target = pq.delMin();
        Board target_twin = pq_twin.delMin();
        // StdOut.println("The removed board is : " + target.toString());
        queue.add(target);
        queue_twin.add(target_twin);
        
        Iterable<Board> neighbors = target.neighbors();
        Iterable<Board> neighbors_twin = target_twin.neighbors();
        
        for (Board a : neighbors) {
            int flag = 0;
            for (Board b : queue) {
                if (a.equals(b)) flag = 1;
                }
            if (flag == 0) {
                a.setPrior(step + a.manhattan());
                pq.insert(a);
                // StdOut.println("The inserted board is : " + a.toString());
                }
            }
        
        for (Board a : neighbors_twin) {
            int flag_twin = 0;
            for (Board b : queue_twin) {
                if (a.equals(b)) flag_twin = 1;
                }
            if (flag_twin == 0) {
                a.setPrior(step + a.manhattan());
                pq_twin.insert(a);
                // StdOut.println("The inserted board is : " + a.toString());
                }
            }
        
        }
    
    
    this.N = step;
    this.result = queue;
    if (pq.min().isGoal()) this.isSolvable = true;
    else this.isSolvable = false;
    
    }           // find a solution to the initial board (using the A* algorithm)
    private class solverComparator implements Comparator<Board> {
        @Override 
        public int compare(Board o1, Board o2) {
            return o1.getPrior() > o2.getPrior() ? 1: -1;
        }
    }
    public boolean isSolvable() {
        return isSolvable;
    }            // is the initial board solvable?
    public int moves() {
        if (isSolvable) return N;
        else return -1;
    }                     // min number of moves to solve initial board; -1 if unsolvable
    public Iterable<Board> solution() {
        if (isSolvable) return result;
        else return null;
    }     // sequence of boards in a shortest solution; null if unsolvable
    public static void main(String[] args) {
       // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    } // solve a slider puzzle (given below)
}
