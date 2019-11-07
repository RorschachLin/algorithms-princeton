import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Solver {

    private CompareBoard resultCB;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        if (initial == null) {
            throw new IllegalArgumentException();
        }

        MinPQ<CompareBoard> minPQ = new MinPQ<>();
        int moveCount = 0;
        minPQ.insert(new CompareBoard(initial, moveCount, null));
        while (!minPQ.isEmpty()) {
            CompareBoard currCompBoard = minPQ.delMin();
            Board currBoard = currCompBoard.getBoard();
            StdOut.println(currBoard.toString());
            StdOut.println("priority=" + currCompBoard.getPriority());
            StdOut.println("moves=" + currCompBoard.getMoves());
            if (currBoard.isGoal()) {
                resultCB = currCompBoard;
                StdOut.println("result board:" + resultCB.getBoard().toString());
                StdOut.println("isgoal" + resultCB.getBoard().isGoal());
                break;
            }

            if (currBoard.hamming() == 2 && currBoard.twin().isGoal()) {
                break;
            }

            Iterable<Board> neighbors = currBoard.neighbors();
            for (Board b : neighbors) {
                if (currCompBoard.getPrev() != null && b.equals(currCompBoard.getPrev().getBoard())) {
                    continue;
                }
                minPQ.insert(new CompareBoard(b, currCompBoard.getMoves() + 1, currCompBoard));
            }
        }
    }

    private class CompareBoard implements Comparable<CompareBoard> {
        private final Board board;
        private final int moves;
        private final CompareBoard prev;

        CompareBoard(Board board, int moves, CompareBoard prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
        }

        @Override
        public int compareTo(CompareBoard that) {
            return this.getPriority() - that.getPriority();
        }

        public Board getBoard() {
            return board;
        }

        public int getMoves() {
            return moves;
        }

        public int getPriority() {
            return board.manhattan() + moves;
        }

        public CompareBoard getPrev() {
            return prev;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return resultCB != null;
    }

    // min number of moves to solve initial board
    public int moves() {
        return resultCB.getMoves();
    }


    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        ArrayList<Board> result = new ArrayList<>();
        CompareBoard currBoard = resultCB;
        while (currBoard.getPrev() != null) {
            result.add(currBoard.getBoard());
            currBoard = currBoard.getPrev();
        }
        return result;
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
