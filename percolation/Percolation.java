package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] field;

    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF helper;

    private int dimension;
    private int opened;

    private boolean left;
    private boolean right;
    private boolean top;
    private boolean bottom;
    private boolean corner;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        uf = new WeightedQuickUnionUF(N * N + 2);
        helper = new WeightedQuickUnionUF(N * N + 1);

        field = new int[N][N];
        dimension = N;
        opened = 0;
        corner = false;
    }

    public void open(int row, int col) {
        if (dimension == 1) {
            opened = 1;
            corner = true;
        }

        if (row >= dimension || col >= dimension) {
            throw new IndexOutOfBoundsException();
        }

        if (isOpen(row, col)) {
            return;
        }
        if (row == 0) {
            field[row][col] = 1; //  fill blue

            uf.union(dimension * dimension, xyTo1D(row, col));
            helper.union(dimension * dimension, xyTo1D(row, col));
        } else if (row == dimension - 1) {
            field[row][col] = 1; // fill white

            uf.union(dimension * dimension + 1, xyTo1D(row, col));
        } else {
            field[row][col] = 1; // fill white
        }

        if (col >= 1 && isOpen(row, col - 1)) {
            uf.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            helper.union(xyTo1D(row, col), xyTo1D(row, col - 1));
        }

        if (col <= dimension - 2 && isOpen(row, col + 1)) {
            uf.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            helper.union(xyTo1D(row, col), xyTo1D(row, col + 1));
        }

        if (row >= 1 && isOpen(row - 1, col)) {

            uf.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            helper.union(xyTo1D(row, col), xyTo1D(row - 1, col));
        }

        if (row <= dimension - 2 && isOpen(row + 1, col)) {
            uf.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            helper.union(xyTo1D(row, col), xyTo1D(row + 1, col));

        }
        opened = opened + 1;
    }

    private int xyTo1D(int row, int col) {
        return row * dimension + col;
    }

    public boolean isOpen(int row, int col) {
        if (corner) {
            return true;
        }
        if (row >= dimension || col >= dimension) {
            throw new IndexOutOfBoundsException();
        }
        return (!(field[row][col] == 0));
    }

    public boolean isFull(int row, int col) {
        if (corner) {
            return true;
        }
        if (row >= dimension || col >= dimension) {
            throw new IndexOutOfBoundsException();
        }

        return helper.connected(xyTo1D(row, col), dimension * dimension);
    }

    public int numberOfOpenSites() {
        return opened;
    }

    public boolean percolates() {
        if (corner) {
            return true;
        }
        return uf.connected(dimension * dimension, dimension * dimension + 1);
    }
    /*
    private void status() {
        for (int[] x : field) {
            for (int y : x) {
                System.out.print(y + "  ");
            }
            System.out.println();
        }
        System.out.println("-----------------");
        System.out.println(uf.connected(1, 2));
        System.out.println(uf.connected(2, 5));
        System.out.println(uf.connected(4, 7));
        System.out.println(uf.connected(4, 5));
        System.out.println(uf.connected(2, 9));
        System.out.println(uf.connected(4, 9));
        System.out.println(uf.connected(7, 9));
        System.out.println(uf.connected(8, 9));

        //System.out.println(uf.connected(12,9));
        //System.out.println(uf.connected(13,6));
        //System.out.println(uf.connected(14,6));
        System.out.println();
        System.out.println("----------------");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                System.out.print(isFull(i, j) + " ");
            }
            System.out.println();
        }

        System.out.println("------------------------");
        System.out.println(numberOfOpenSites() + " open sites");
    }
    */
    public static void main(String[] args) {

        Percolation perc = new Percolation(3);
        perc.open(1, 1);
        perc.open(1, 2);
        perc.open(2, 1);
        //perc.open(3,0);
        //perc.open(3,2);
        //perc.open(3,1);
        perc.open(0, 2);
        //perc.status();

    }
}
