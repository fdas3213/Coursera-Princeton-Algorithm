import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// create n-by-n grid, with all sites blocked
public class Percolation {
    private WeightedQuickUnionUF id;
    private boolean openSites[];
    private int gridSize;
    private static int numOpensites = 0;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.gridSize = n;
        this.id = new WeightedQuickUnionUF((n*n) + 2);
        this.openSites = new boolean[(n*n)+2];
    }

    private int getIndex(int i, int j){
        if (i < 1 || i > this.gridSize || j < 1 || j > this.gridSize) {
            throw new IllegalArgumentException();
        }
        return (i-1) * this.gridSize + j;
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col){
        if (row < 1 || row > this.gridSize || col < 1 || col > this.gridSize){
            throw new IllegalArgumentException();
        }
        int index = getIndex(row,col);
        if (!isOpen(row, col)){
            openSites[row*col + 1] = true;
        }
        //if firstrow or last row, connect to fake top and bottom
        if (row == 1) id.union(index, 0);
        if (row == this.gridSize) id.union(index, (this.gridSize * this.gridSize + 1));

        if (col > 1 && isOpen(row, col - 1)){
            id.union(index, (row * (col - 1) + 1));
        }
        if (col < this.gridSize && isOpen(row, col + 1)) {
            id.union(index, (row * (col + 1) + 1));
        }
        if (row > 1 && isOpen(row-1, col)){
            id.union(index, ((row-1)*col + 1));
        }
        if (row < this.gridSize && isOpen(row + 1, col)){
            id.union(index, ((row + 1) * col + 1));
        }
        numOpensites ++;

    }

    public boolean isOpen(int row, int col){
        int index = getIndex(row, col);
        return openSites[index] == true;
    }

    public int numberOfOpenSites(){
        return numOpensites;
    }

    public boolean percolates(){
        return id.connected(0, gridSize*gridSize+1);
    }

    public boolean isFull(int row, int col){
        if (row > 1 && row <= gridSize && col > 1 && col <= gridSize){
            return id.connected(getIndex(row,col), 0);
        }else{
            throw new IllegalArgumentException();
        }
    }

}
