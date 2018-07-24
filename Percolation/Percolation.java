import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// create n-by-n grid, with all sites blocked
public class Percolation {
    private WeightedQuickUnionUF id;
    private WeightedQuickUnionUF normalID;
    private boolean openSites[];
    private int gridSize;
    private final int topIndex;
    private final int bottomIndex;
    private static int numOpenSites = 0;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must > 0xs");
        }
        gridSize = n;
        topIndex = 0;
        bottomIndex = n*n+1;
        id = new WeightedQuickUnionUF(n * n + 2);
        normalID = new WeightedQuickUnionUF(n * n + 1);
        openSites = new boolean[n * n + 2];
        openSites[topIndex] = true;
        openSites[bottomIndex] = true;
    }

    private int getIndex(int i, int j){
        if (i < 1 || i > gridSize || j < 1 || j > gridSize) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        return (i-1) * gridSize + j;
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col){
        if (row < 1 || row > gridSize || col < 1 || col > gridSize){
            throw new IllegalArgumentException("Initial index out of bounds");
        }

        int index = getIndex(row,col);
        openSites[index] = true;
        numOpenSites++;

        //if firstrow or last row, connect to fake top and bottom
        if (row == 1) {
            id.union(index, topIndex);
            normalID.union(index, topIndex);
        }
        if (row == gridSize) id.union(index, bottomIndex);

        unionTwo(row, col, row, col - 1); //west
        unionTwo(row, col, row, col + 1); //east
        unionTwo(row, col, row - 1, col); //north
        unionTwo(row, col, row + 1, col); //south
    }

    private void unionTwo(int rowA, int colA, int rowB, int colB){
        if (rowB > 0 && rowB <= gridSize && colB > 0 && colB <= gridSize
                && isOpen(rowB, colB)) {
            id.union(getIndex(rowA, colA), getIndex(rowB, colB));
            normalID.union(getIndex(rowA, colA), getIndex(rowB, colB));
        }
    }

    public boolean isOpen(int row, int col){
        return openSites[getIndex(row, col)];
    }

    public int numberOfOpenSites(){
        return numOpenSites;
    }

    public boolean percolates(){
        return id.connected(topIndex, bottomIndex);
    }

    public boolean isFull(int row, int col){
        if (row > 0 && row <= gridSize && col > 0 && col <= gridSize){
            return normalID.connected(topIndex, getIndex(row,col));
        }else{
            throw new IllegalArgumentException();
        }
    }

}
