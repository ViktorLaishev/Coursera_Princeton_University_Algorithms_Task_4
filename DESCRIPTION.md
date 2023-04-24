# Coursera_Princeton_University_Algorithms_Task_4
Write a program to solve the 8-puzzle problem (and its natural generalizations) using the A* search algorithm.
The problem. The 8-puzzle is a sliding puzzle that is played on a 3-by-3 grid with 8 square tiles labeled 1 through 8, plus a blank square. The goal is to rearrange the tiles so that they are in row-major order, using as few moves as possible. You are permitted to slide tiles either horizontally or vertically into the blank square. The following diagram shows a sequence of moves from an initial board (left) to the goal board (right).
Board data type. To begin, create a data type that models an n-by-n board with sliding tiles. Implement an immutable data type Board with the following API:
public class Board {

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles)
                                           
    // string representation of this board
    public String toString()

    // board dimension n
    public int dimension()

    // number of tiles out of place
    public int hamming()

    // sum of Manhattan distances between tiles and goal
    public int manhattan()

    // is this board the goal board?
    public boolean isGoal()

    // does this board equal y?
    public boolean equals(Object y)

    // all neighboring boards
    public Iterable<Board> neighbors()

    // a board that is obtained by exchanging any pair of tiles
    public Board twin()
    

    // unit testing (not graded)
    public static void main(String[] args)

}
Constructor.  You may assume that the constructor receives an n-by-n array containing the n^2 integers between 0 and n^2 − 1, where 0 represents the blank square. You may also assume that 2 ≤ n < 128.
String representation.  The toString() method returns a string composed of n + 1 lines. The first line contains the board size n; the remaining n lines contains the n-by-n grid of tiles in row-major order, using 0 to designate the blank square.
Hamming and Manhattan distances.  To measure how close a board is to the goal board, we define two notions of distance. The Hamming distance betweeen a board and the goal board is the number of tiles in the wrong position. The Manhattan distance between a board and the goal board is the sum of the Manhattan distances (sum of the vertical and horizontal distance) from the tiles to their goal positions.
Comparing two boards for equality.  Two boards are equal if they are have the same size and their corresponding tiles are in the same positions. The equals() method is inherited from java.lang.Object, so it must obey all of Java’s requirements.

Neighboring boards.  The neighbors() method returns an iterable containing the neighbors of the board. Depending on the location of the blank square, a board can have 2, 3, or 4 neighbors
