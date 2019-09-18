import java.util.ArrayList;

public class Board {
    private final int[][] allTiles;
    private final int n;
    private int blankRow;
    private int blankCol;

    private int[][] copyOf(int[][] tiles) {
        int[][] thisTiles = new int[tiles.length][];
        for (int i = 0; i < tiles.length; i++) {
            thisTiles[i] = tiles[i].clone();
        }
        return thisTiles;
    }

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.allTiles = copyOf(tiles);
        this.n = allTiles.length;
        for (int row = 0; row < dimension(); row++) {
            for (int col = 0; col < dimension(); col++) {
                if (allTiles[row][col] == 0) {
                    blankRow = row;
                    blankCol = col;
                }
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(dimension());
        sb.append("\n");
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                sb.append(" ");
                sb.append(String.format("%3d", allTiles[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int index = 0;
        for (int row = 0; row < dimension(); row++) {
            for (int col = 0; col < dimension(); col++) {
                if (allTiles[row][col] == 0) {
                    continue;
                }
                if (row * dimension() + col + 1 != allTiles[row][col]) {
                    index++;
                }
            }
        }

        if (allTiles[dimension() - 1][dimension() - 1] == dimension() * dimension() - 1) index++;

        return index;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int distance = 0;
        for (int row = 0; row < dimension(); row++) {
            for (int col = 0; col < dimension(); col++) {
                if (allTiles[row][col] == 0) {
                    continue;
                }
                int val = allTiles[row][col] - 1;
                int horizDistance = Math.abs(val / dimension() - row);
                int vertDistance = Math.abs(val % dimension() - col);
                distance = distance + horizDistance + vertDistance;
            }
        }
        return distance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int row = 0; row < dimension(); row++) {
            for (int col = 0; col < dimension(); col++) {
                if (allTiles[dimension() - 1][dimension() - 1] == 0) {
                    continue;
                }
                if (allTiles[row][col] != (row * dimension() + col + 1)) {
                    return false;
                }
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        Board yBoard = (Board) y;
        if (this.dimension() != yBoard.dimension()) {
            return false;
        }
        for (int row = 0; row < dimension(); row++) {
            for (int col = 0; col < dimension(); col++) {
                if (this.allTiles[row][col] != yBoard.allTiles[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<>();
        if (blankRow > 0) {
            int[][] swappedCopy = swap(blankRow, blankCol, blankRow - 1, blankCol);
            Board neighbor = new Board(swappedCopy);
            neighbors.add(neighbor);
        }
        if (blankRow < dimension() - 1) {
            int[][] swappedCopy = swap(blankRow, blankCol, blankRow + 1, blankCol);
            Board neighbor = new Board(swappedCopy);
            neighbors.add(neighbor);
        }
        if (blankCol > 0) {
            int[][] swappedCopy = swap(blankRow, blankCol, blankRow, blankCol - 1);
            Board neighbor = new Board(swappedCopy);
            neighbors.add(neighbor);
        }
        if (blankCol < dimension() - 1) {
            int[][] swappedCopy = swap(blankRow, blankCol, blankRow, blankCol + 1);
            Board neighbor = new Board(swappedCopy);
            neighbors.add(neighbor);
        }
        return neighbors;
    }

    private int[][] swap(int row1, int col1, int row2, int col2) {
        int[][] copyTiles = copyOf(allTiles);
        int temp = copyTiles[row1][col1];
        copyTiles[row1][col1] = copyTiles[row2][col2];
        copyTiles[row2][col2] = temp;
        return copyTiles;
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twinCopy;
        if (blankRow == 0) {
            twinCopy = swap(1, 0, 1, 1);
        } else {
            twinCopy = swap(0, 0, 0, 1);
        }
        return new Board(twinCopy);
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }

}
