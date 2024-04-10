package org.sk.problem;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.Arrays;

@ApplicationScoped
public class ProblemRepository {
    // Temporary in-memory storage instead of a DB
    private static final ArrayList<Problem> problems = new ArrayList<>();
    private static long nextId = 0;

    public ArrayList<Problem> getAllProblems() {
        return problems;
    }

    public Problem getProblemById(long id) {
        return problems.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    public Problem getProblemByGrid(boolean[][] grid) {
        return problems.stream().filter(p -> Arrays.deepEquals(p.getGrid(), grid)).findFirst().orElse(null);
    }

    public Problem storeProblem(boolean[][] problem) {
        Problem createdProblem = new Problem(problem, nextId);
        problems.add(createdProblem);
        nextId++;
        return createdProblem;
    }

}
