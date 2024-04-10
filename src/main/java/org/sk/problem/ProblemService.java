package org.sk.problem;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.sk.exception.NoProblemWithSpecifiedIdException;
import org.sk.exception.ProblemAlreadyExistsException;
import org.sk.game.GameService;
import org.sk.solution.Solution;
import org.sk.solution.SolutionService;

import java.util.ArrayList;

@ApplicationScoped
public class ProblemService {
    @Inject
    private ProblemRepository problemRepository = new ProblemRepository();

    @Inject
    private SolutionService solutionService = new SolutionService();

    @Inject
    private GameService gameService = new GameService();

    public ArrayList<Problem> getAllProblems() {
        return problemRepository.getAllProblems();
    }

    public Problem getProblemById(long id) {
        Problem problem = problemRepository.getProblemById(id);

        if (problem != null) {
            return problem;
        } else {
            throw new NoProblemWithSpecifiedIdException(id);
        }
    }

    public Solution storeAndSolveProblem(boolean[][] problem) {
        // Check if the problem already exists
        Problem potentialExistingProblem = problemRepository.getProblemByGrid(problem);

        if (potentialExistingProblem == null) {
            boolean[][] solution =  gameService.solveLightsOutProblem(problem);
            if(solution == null){
                return new Solution(-1, null, -1);
            } else {
                Problem createdProblem = problemRepository.storeProblem(problem);
                int[][] solutionSteps = gameService.getSolutionSteps(solution);
                return solutionService.storeSolution(solution, createdProblem.getId(), solutionSteps);
            }


        } else {
            throw new ProblemAlreadyExistsException(potentialExistingProblem.getId());
        }




    }


}
