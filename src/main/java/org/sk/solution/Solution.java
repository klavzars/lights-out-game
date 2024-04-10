package org.sk.solution;

public class Solution {
    private final long id;
    private final long problemId;
    private final boolean[][] grid;

    public Solution(long id, boolean[][] grid, long problemId) {
        this.id = id;
        this.grid = grid;
        this.problemId = problemId;
    }

    public long getId() {
        return id;
    }

    public long getProblemId() {
        return problemId;
    }

    public boolean[][] getGrid() {
        return grid;
    }
}
