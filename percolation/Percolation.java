/* *****************************************************************************
 *  Name:Rorschach Lin
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int[] gridState; //0-bolcked; 1-opened;
    private final int dimention;
    private final int headRoot;
    private final int tailRoot;
    private final WeightedQuickUnionUF wUf_all;
    private final WeightedQuickUnionUF wUF_head;
    private int NUM_OPENSITE;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.dimention = n;
        this.wUf_all = new WeightedQuickUnionUF(dimention * dimention + 2);
        this.wUF_head = new WeightedQuickUnionUF(dimention * dimention + 1);
        this.gridState = new int[dimention * dimention];
        this.headRoot = dimention * dimention;
        this.tailRoot = dimention * dimention + 1;
        this.NUM_OPENSITE = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > dimention || col < 1 || col > dimention) {
            throw new IllegalArgumentException();
        }

        row = row - 1;
        col = col - 1;
        int index = siteIndex(row, col);
        if (gridState[index] != 1) {
            NUM_OPENSITE++;
        }
        else {
            return;
        }
        gridState[index] = 1;

        // connect head root and set it to full
        if (row == 0) {
            unionBoth(headRoot, index);
        }
        if (row == (dimention - 1)) { // connect tail root
            wUf_all.union(tailRoot, index);
        }
        // union surrounding site
        int upper = getUpperIndex(row, col);
        if (upper != -1 && gridState[upper] == 1) {
            unionBoth(index, upper);
        }
        int lower = getLowerIndex(row, col);
        if (lower != -1 && gridState[lower] == 1) {
            unionBoth(index, lower);
        }
        int left = getLeftIndex(row, col);
        if (left != -1 && gridState[left] == 1) {
            unionBoth(index, left);
        }
        int right = getRightIndex(row, col);
        if (right != -1 && gridState[right] == 1) {
            unionBoth(index, right);
        }

    }

    // union both WeightedQuickUnionUF
    private void unionBoth(int site1, int site2) {
        wUf_all.union(site1, site2);
        wUF_head.union(site1, site2);
    }

    // get upper index
    private int getUpperIndex(int row, int col) {
        if (row - 1 >= 0) {
            return siteIndex(row - 1, col);
        }
        return -1;
    }

    // get lower index
    private int getLowerIndex(int row, int col) {
        if (row + 1 < dimention) {
            return siteIndex(row + 1, col);
        }
        return -1;
    }

    // get left index
    private int getLeftIndex(int row, int col) {
        if (col - 1 >= 0) {
            return siteIndex(row, col - 1);
        }
        return -1;
    }

    // get right index
    private int getRightIndex(int row, int col) {
        if (col + 1 < dimention) {
            return siteIndex(row, col + 1);
        }
        return -1;
    }

    // grid index in 1 dimention
    private int siteIndex(int row, int col) {
        return row * dimention + col;
    }

    // private int indexToRow(int index) {
    //     return index / dimention;
    // }
    //
    // private int indexToCol(int index) {
    //     return index % dimention;
    // }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > dimention || col < 1 || col > dimention) {
            throw new IllegalArgumentException();
        }
        row = row - 1;
        col = col - 1;
        if (gridState[siteIndex(row, col)] == 1) {
            return true;
        }
        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > dimention || col < 1 || col > dimention) {
            throw new IllegalArgumentException();
        }
        row = row - 1;
        col = col - 1;
        int index = siteIndex(row, col);
        if (wUF_head.connected(index, headRoot)) {
            return true;
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return NUM_OPENSITE;
    }

    // does the system percolate?
    public boolean percolates() {
        if (wUf_all.connected(headRoot, tailRoot)) {
            return true;
        }
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {

    }

}
