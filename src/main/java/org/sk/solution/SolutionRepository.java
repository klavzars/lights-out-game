package org.sk.solution;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;

@ApplicationScoped
public class SolutionRepository {
    // -------------------------------------------
    // Temporary in-memory storage instead of a DB
    // -------------------------------------------
    private static final ArrayList<Solution> solutions = new ArrayList<>();
    private static final ArrayList<SolutionStep> solutionSteps = new ArrayList<>();
    private static long nextId = 0;

    // -------------------------------------------

    public ArrayList<Solution> getAllSolutions() {
        return solutions;
    }

    public Solution getSolutionByProblemId(long problemId) {
        return solutions.stream().filter(s -> s.getProblemId() == problemId).findFirst().orElse(null);
    }

    public Solution storeSolution(boolean[][] grid, long problemId, int[][] steps) {
        Solution createdSolution = new Solution(nextId, grid, problemId);
        solutions.add(createdSolution);
        nextId++;

        long nextStepId = 0;
        if (steps != null) {
            for (int[] step : steps) {
                solutionSteps.add(new SolutionStep(step[0], step[1], nextStepId, nextId));
                nextStepId++;
            }
        }
        return createdSolution;
    }

}
