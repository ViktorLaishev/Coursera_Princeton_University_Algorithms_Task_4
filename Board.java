import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Board {
    private int [][] blocksArray;
    private int n;
    private int manHa = -1;
    private int hanMin = -1;

    public Board(int[][] blocks) {
        if (blocks == null)
            throw new java.lang.IllegalArgumentException();
         n = blocks.length;
         blocksArray = new int[blocks.length][blocks[1].length];
        for (int i=0; i<n;i++) {
            blocksArray[i] = blocks[i].clone();
        }
    }         // construct a board from an n-by-n array of blocks, // (where blocks[i][j] = block in row i, column j)

    public int dimension()
    { return n; }                 // board dimension n


    public int hamming(){
        if (hanMin == -1){
            int hamming=0;
            for (int i=0;i<n;i++)
                for (int j=0;j<blocksArray[i].length;j++){
                    if (blocksArray[i][j] != i*n+j+1 && blocksArray[i][j] != 0)
                        hamming++;
                }
            hanMin = hamming;
        }
        return hanMin;
    }// number of blocks out of place

    public int manhattan() {
        if (manHa == -1) {
            int manhattan = 0;
            for (int i = 0; i < n; i++)
                for (int j = 0; j < blocksArray[i].length; j++) {
                    if (blocksArray[i][j] != i * n + j + 1 && blocksArray[i][j] != 0) {
                        int item = blocksArray[i][j];
                        int row = (item - 1) / n;
                        int col = item - 1 - row * n;
                        manhattan += Math.abs(row - i) + Math.abs(col - j);
                    }
                }
            manHa = manhattan;
        }
        return manHa;
    }                // sum of Manhattan distances between blocks and goal


    public boolean isGoal()
    { return hamming()==0; }// is this board the goal board?


    public Board twin(){
        int[][] twinBoardArray = new int[n][n];
        for (int i = 0; i<n;i++)
            twinBoardArray[i]=blocksArray[i].clone();
        if (twinBoardArray[0][0]!=0 && twinBoardArray[0][1]!=0)
            swap(twinBoardArray,0,0,0,1);
        else
            swap(twinBoardArray,1,0,1,1);
       return new Board(twinBoardArray);
    }                    // a board that is obtained by exchanging any pair of blocks

    public boolean equals(Object y){
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass())
            return false;
        Board that = (Board) y;
        return  (Arrays.deepEquals(this.blocksArray,that.blocksArray));
    }        // does this board equal y?

    private void swap(int[][] blocksArray, int i, int j, int m, int k){
        int temp = blocksArray[i][j];
       blocksArray[i][j] = blocksArray[m][k];
       blocksArray[m][k] = temp;
    }

    public Iterable<Board> neighbors(){
        Queue<Board> neighbors = new Queue<>();
        int blankRow=0, blankCol=0;
        for(int i=0;i<n;i++)
            for (int j=0;j<blocksArray[i].length;j++)
                if (blocksArray[i][j]==0) {
                    blankRow=i;
                    blankCol=j;
                }
        if (blankRow > 0 && blankRow < n){
            int[][] blocksCopy = new int[n][n];
            for (int i =0; i<n; i++)
                blocksCopy[i]= blocksArray[i].clone();
            swap(blocksCopy,blankRow,blankCol,blankRow-1,blankCol);
            Board neighborBlock = new Board(blocksCopy);
            neighbors.enqueue(neighborBlock);
        }

        if (blankRow < n-1){
            int[][] blocksCopy = new int[n][n];
            for (int i =0; i<n; i++)
                blocksCopy[i]= blocksArray[i].clone();
            swap(blocksCopy,blankRow,blankCol,blankRow+1,blankCol);
            Board neighborBlock = new Board(blocksCopy);
            neighbors.enqueue(neighborBlock);
        }

        if (blankCol> 0){
            int[][] blocksCopy = new int[n][n];
            for (int i =0; i<n; i++)
                blocksCopy[i]= blocksArray[i].clone();
            swap(blocksCopy,blankRow,blankCol,blankRow,blankCol-1);
            Board neighborBlock = new Board(blocksCopy);
            neighbors.enqueue(neighborBlock);
        }
        if (blankCol < n-1){
            int[][] blocksCopy = new int[n][n];
            for (int i =0; i<n; i++)
                blocksCopy[i]= blocksArray[i].clone();
            swap(blocksCopy,blankRow,blankCol,blankRow,blankCol+1);
            Board neighborBlock = new Board(blocksCopy);
            neighbors.enqueue(neighborBlock);
        }
        return neighbors;
    }     // all neighboring boards


    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", blocksArray[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }              // string representation of this board (in the output format specified below)

    public static void main(String[] args){
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        StdOut.println(initial);
        StdOut.println(initial.hamming());
        StdOut.println(initial.manhattan());
        StdOut.println(initial.dimension());
        StdOut.println(initial.twin());
        StdOut.println(initial.neighbors());
        StdOut.println(initial.isGoal());
    }
}
