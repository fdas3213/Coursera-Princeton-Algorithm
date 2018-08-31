import edu.princeton.cs.algs4.StdOut;
import java.lang.*;
import java.util.Arrays;

public class Board {

    private int[] gameBoard;
    private int dimension;
    private int[] goalBoard;

    // construct a board from an n-by-n array of blocks,(where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        if (blocks.length < 2 || blocks.length >= 128) {
            throw new IllegalArgumentException("Please supply a blocks of dimension in [2, 128)");
        }
        // initialize dimension
        dimension = blocks.length;
        // initialize goalboard
        goalBoard = new int[dimension * dimension];
        for (int i = 0; i < dimension*dimension-1; i ++){
            goalBoard[i] = i+1;
        }
        goalBoard[dimension*dimension - 1] = 0;
        // initialize blocks to gameboard
        int k = 0;
        gameBoard = new int[dimension * dimension];
        for(int i = 0; i < blocks.length; i++){
            for (int j = 0; j < blocks[i].length; j++){
                gameBoard[k++] = blocks[i][j];
            }
        }
    }

    // board dimension n
    public int dimension() {
        return dimension;
    }

    // number of blocks out of place
    public int hamming() {
        int outofplace = 0;
        for (int i = 0; i < gameBoard.length; i++){
            if (goalBoard[i] != 0 && gameBoard[i] != goalBoard[i]){
                outofplace++;
            }
        }
        return outofplace;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int outofplace = 0;
        int new_i = 0, new_j = 0;
        for (int k = 0; k < gameBoard.length; k++){
            if (gameBoard[k] != 0) {
                int idx = gameBoard[k]-1;
                int old_i = idx / dimension;
                int old_j = idx - old_i*dimension;
                outofplace += Math.abs(old_i - new_i) + Math.abs(old_j - new_j);
            }
            if ((k+1) % dimension ==0){
                new_i ++;
                new_j = 0;
            }else new_j ++;
        }
        return outofplace;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return Arrays.equals(gameBoard,goalBoard);
    }

    // string representation of this board (in the output format specified below)
    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append(dimension + "\n");
        for (int i = 0; i < gameBoard.length; i++){
            s.append(String.format("%2d ", gameBoard[i]));
            if ((i+1) % 3 == 0) s.append("\n");
        }
        return s.toString();
    }

    /*
    // all neighboring boards
    public Iterable<Board> neighbors(){

    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin(){

    }
    */

    // does this board equal y?
    @Override
    public boolean equals(Object y){
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return (this.dimension == that.dimension) && (this.gameBoard == that.gameBoard);
    }

    // unit tests (not graded)
    public static void main(String[] args){
        int[][] test = {{8,1,3}, {4,0,2}, {7,6,5}};
        // test dimension and toString()
        Board tBoard = new Board(test);
        StdOut.println(tBoard.toString());
        // test hamming and manhattan priority
        StdOut.println("hamming priority should be 5, and is: " + tBoard.hamming());
        StdOut.println("manhattan priority should be 10, and is: " + tBoard.manhattan());
    }
}