import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
     private boolean isSolvable;
    private int moveSteps;
    private Stack<Board> solution;
    private Node goal;
    private Board first;

    private class Node implements Comparable<Node> {
        private Board board;
        private int moves;
        private int priority;
        private Node father;
        private int manhattan;
        private boolean isTwin;

        public Node(Board initialBd, Node itsFather, boolean isTwin) {
            this.board = initialBd;
            this.father = itsFather;
            this.manhattan = board.manhattan();
            this.isTwin = isTwin;

            if (this.father != null) {
                this.moves = this.father.moves + 1;
                this.priority = manhattan + this.moves;
            }
            else {
                this.priority = manhattan;
                this.moves = 0;
            }
        }

        public int compareTo(Node that) {
            if (this.priority == that.priority)
                return this.manhattan - that.manhattan;
            else return this.priority - that.priority;
        }
    }

    public Solver(Board initial) {
        if (initial == null)
            throw new java.lang.IllegalArgumentException();
        solution = new Stack<Board>();
        first = initial;
        Node rootNode = new Node(initial, null, false);
        Node rootNodeTwin = new Node(initial.twin(), null, true);
        MinPQ<Node> gamePQ = new MinPQ<>();
        gamePQ.insert(rootNode);
        gamePQ.insert(rootNodeTwin);
        Node currentNode = gamePQ.delMin();

        while (true) {
            if (currentNode.board.isGoal()) {
                if (currentNode.isTwin) {
                    moveSteps = -1;
                    isSolvable = false;
                } else {
                    isSolvable = true;
                    moveSteps = currentNode.moves;
                    goal = currentNode;
                }
                break;
            } else {
                for (Board neighbor : currentNode.board.neighbors()) {
                    Node neighborNode = new Node(neighbor, currentNode, currentNode.isTwin);
                    if (currentNode.father == null)
                        gamePQ.insert(neighborNode);
                    else if (!currentNode.father.board.equals(neighbor))
                        gamePQ.insert(neighborNode);
                }
            }
            currentNode = gamePQ.delMin();
        }// find a solution to the initial board (using the A* algorithm)
    }

    public boolean isSolvable() {
        return isSolvable;
    }// is the initial board solvable?


    public int moves() {
        if (!isSolvable())
            return -1;
        return moveSteps;
    }                     // min number of moves to solve initial board; -1 if unsolvable

    public Iterable<Board> solution() {
        if (!isSolvable())
            return null;
        while (goal.father != null) {
            solution.push(goal.board);
            goal = goal.father;
        }
        solution.push(first);
        return solution;
    }      // sequence of boards in a shortest solution; null if unsolvable


    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        Solver solver = new Solver(initial);
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        } // solve a slider puzzle (given below)
    }
}
