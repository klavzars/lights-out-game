package org.sk.solution;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.sk.exception.NoProblemWithSpecifiedIdException;

import java.util.ArrayList;
@ApplicationScoped
public class SolutionService {
    @Inject
    private SolutionRepository solutionRepository = new SolutionRepository();

    public ArrayList<Solution> getAllSolutions() {
        return solutionRepository.getAllSolutions();
    }

    public Solution getSolutionByProblemId(long problemId) {
        Solution solution = solutionRepository.getSolutionByProblemId(problemId);
        if (solution != null) {
            return solution;
        } else {
            // Took a bit of a shortcut here - assuming everytime a solution is
            // not found, it's because the problem is not found
            throw new NoProblemWithSpecifiedIdException(problemId);
        }
    }

    public Solution storeSolution(boolean[][] grid, long problemId, int[][] solutionSteps) {
        return solutionRepository.storeSolution(grid, problemId, solutionSteps);
    }
}
