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
        for (int i = 0; i < goalBoard.length; i++){
            if (goalBoard[i] != 0) {
                int idx = goalBoard[i]-1;
                int old_i = idx / dimension;
                int old_j = idx - old_i*dimension;
                outofplace += Math.abs(old_i - new_i) + Math.abs(old_j - new_j);
            }
            if (i% dimension ==0){
                new_i = 0;
                new_j ++;
            }
            new_i ++;
        }
        return outofplace;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return Arrays.equals(gameBoard,goalBoard);
    }
    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    public boolean equals(Object y)        // does this board equal y?
    public Iterable<Board> neighbors()     // all neighboring boards
    public String toString()               // string representation of this board (in the output format specified below)

    public static void main(String[] args) // unit tests (not graded)
}