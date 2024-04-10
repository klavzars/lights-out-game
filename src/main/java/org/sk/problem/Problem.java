package org.sk.problem;

public class Problem {
    private final long id;
    private final boolean[][] grid;

    public Problem(boolean[][] grid, long id) {
        this.grid = grid;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public boolean[][] getGrid() {
        return grid;
    }


}
