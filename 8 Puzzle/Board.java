import edu.princeton.cs.algs4.StdOut;
import java.lang.*;
import java.util.Arrays;
import java.util.Stack;

public class Board {

    private final int dimension;
    private int blankRow;
    private int blankCol;
    private int[] gameBoard;
    private final int[] goalBoard;

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
        gameBoard = new int[dimension * dimension];
        for(int i = 0; i < blocks.length; i++){
            for (int j = 0; j < blocks[i].length; j++){
                if(blocks[i][j] == 0){
                    blankRow = i;
                    blankCol = j;
                }
                gameBoard[i*dimension+j] = blocks[i][j];
            }
        }
    }

    private int[] copyOf(int[] mat){
        int[] blocks = new int[mat.length];
        for (int i =0;i < mat.length; i++){
            blocks[i] = mat[i];
        }
        return blocks;
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

    // get index of blank spot
    private int getIndex(){
        return blankRow*dimension + blankCol;
    }

    //swap blank id with neigh ids
    private void swap(int[] copyBoard, int oldIdx, int newIdx){
        int temp = copyBoard[oldIdx];
        copyBoard[oldIdx] = copyBoard[newIdx];
        copyBoard[newIdx] = temp;
    }

    private int[][] to2D(int[] arr){
        int[][] arr2D = new int[dimension][dimension];
        for(int i = 0;i<dimension;i++){
            for(int j =0;j < dimension; j++){
                arr2D[i][j] = arr[i*dimension+j];
            }
        }
        return arr2D;
    }

    // all neighboring boards
    public Iterable<Board> neighbors(){
        Stack<Board> stackofBoards = new Stack<>();

        if(blankRow > 0){
            int[] north = copyOf(gameBoard);
            swap(north, getIndex(), getIndex()-dimension);
            stackofBoards.push(new Board(to2D(north)));
        }
        if(blankRow < dimension - 1){
            int[] south = copyOf(gameBoard);
            swap(south, getIndex(), getIndex()+dimension);
            stackofBoards.push(new Board(to2D(south)));
        }
        if(blankCol > 0){
            int[] west = copyOf(gameBoard);
            swap(west, getIndex(), getIndex()-1);
            stackofBoards.push(new Board(to2D(west)));
        }
        if(blankCol < dimension-1){
            int[] east= copyOf(gameBoard);
            swap(east, getIndex(),getIndex()+1);
            stackofBoards.push(new Board(to2D(east)));
        }
        return stackofBoards;
    }


    // a board that is obtained by exchanging any pair of blocks
    public Board twin(){
        int[] copy = copyOf(gameBoard);
        if (blankRow != 0) {
            swap(copy, 0,1);
        }else{
            swap(copy, dimension,dimension+1);
        }
        return new Board(to2D(copy));
    }

    // does this board equal y?
    @Override
    public boolean equals(Object y){
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (that.dimension != this.dimension) return false;
        if (that.blankCol != this.blankCol) return false;
        if (that.blankRow != this.blankRow) return false;
        for (int i = 0; i < dimension*dimension; i++){
            if (that.gameBoard[i] != this.gameBoard[i]) return false;
        }
        return true;
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
        // test neighbors
        StdOut.println("\n test neighbors()");
        for(Board b: tBoard.neighbors()){
            StdOut.println(b.toString());
        }
    }
}