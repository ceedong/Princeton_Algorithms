import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.Comparator;
import java.util.Queue;
import java.util.LinkedList;

public class Solver {
    
    private Iterable<SearchNode> result;
    private boolean isSolvable;
    private SearchNode solutionNode;
    public Solver(Board initial) {
        
    if (initial == null) throw new java.lang.IllegalArgumentException();  
    
    MinPQ<SearchNode> pq = new MinPQ<SearchNode>(new solverComparator());
    MinPQ<SearchNode> pq_twin = new MinPQ<SearchNode>(new solverComparator());
    
    Queue<SearchNode> queue = new LinkedList<>();
    Queue<SearchNode> queue_twin = new LinkedList<>();
    
    pq.insert(new SearchNode(initial, 0, null));
    pq_twin.insert(new SearchNode(initial.twin(), 0, null));
    
    int step = 0;
    
    while (true) {
        
        SearchNode curNode = pq.delMin();
        Board curBoard = curNode.getBoard();
      
        if (curBoard.isGoal()) {
            isSolvable = true;
            solutionNode = curNode;
            break;
        }
        
        if (curBoard.hamming() == 2 && curBoard.twin().isGoal()) {
            isSolvable = false;
            break;
        }
          
        int moves = curNode.getMove();
        Board prevBoard = moves > 0 ? curNode.getPrev().getBoard() : null;
        
        for (Board nextBoard : curBoard.neighbors()) {
                if (prevBoard != null && nextBoard.equals(prevBoard)) {
                    continue;
                }
                pq.insert(new SearchNode(nextBoard, moves + 1, curNode));
            }
    
        }
    
    }
    private class solverComparator implements Comparator<SearchNode> {
        @Override 
        public int compare(SearchNode o1, SearchNode o2) {
            
            return o1.getPrior() - o2.getPrior();
            
            }
            
          //  return o1.getPrior() >= o2.getPrior() ? 1 : -1;
        }
    
    public boolean isSolvable() {
        return isSolvable;
    }            // is the initial board solvable?
    public int moves() {
        if (isSolvable) return solutionNode.getMove();
        else return -1;
    }                     // min number of moves to solve initial board; -1 if unsolvable
    public Iterable<Board> solution() {
        if (isSolvable) {
        Queue<Board> resultToReturn = new LinkedList<>();
        SearchNode node = solutionNode;
        while (node != null) {
            resultToReturn.add(node.getBoard());
            node = node.getPrev();
        }
        return resultToReturn;
        } 
        else return null;
    }     // sequence of boards in a shortest solution; null if unsolvable
    
    private class SearchNode {
        
        // some member variables
        private final Board board;
        private int move;
        private SearchNode prevNode;
        // constructor
        public SearchNode(Board board, int moves, SearchNode prevNode) {
            this.board = board;
            this.move = moves;
            this.prevNode = prevNode;
        }
        
         // getter and setter for the priorNum 
         public int getPrior() {
             return board.manhattan() + move;
         }
         
         public Board getBoard() {
             return board;
         }
         
         public int getMove() {
             return move;
         }
         
         public SearchNode getPrev() {
             return prevNode;
         }
    }
          
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
