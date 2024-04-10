package org.sk.solution;

public class SolutionStep {
    private final long solutionId;
    private final long stepNumber;
    private final int row;
    private final int col;



    public SolutionStep(int x, int y, long stepNumber, long solutionId) {
        this.row = x;
        this.col = y;
        this.stepNumber = stepNumber;
        this.solutionId = solutionId;
    }

    public int[] getStepPosition() {
        return new int[]{row, col};
    }

    public long getStepNumber() {
        return stepNumber;
    }

    public long getSolutionId() {
        return solutionId;
    }
}
