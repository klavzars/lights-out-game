package org.sk.game;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

import java.util.ArrayList;

@ApplicationScoped
public class GameService {

    private static final Logger LOG = Logger.getLogger(GameService.class);

    // Includes logging
    public boolean[][] solveLightsOutProblem(boolean[][] grid) {

        LOG.info("----------------------------------");
        LOG.info("STARTED SOLVING LIGHTS OUT PROBLEM");
        long startTime = System.nanoTime();
        boolean[][] result = solveLightsOut(grid);
        long endTime = System.nanoTime();
        LOG.info("FINISHED SOLVING LIGHTS OUT PROBLEM");
        LOG.info("Time taken to solve Lights Out problem: "
                + (endTime - startTime) + " ns" + " = " + (float) (endTime - startTime) / 1000000 + " ms");
        return result;
    }

    public int[][] getSolutionSteps(boolean[][] solutionGrid) {
        int size = solutionGrid.length;
        ArrayList<int[]> steps = new ArrayList<>();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (solutionGrid[row][col]) {
                    steps.add(new int[]{row, col});
                }
            }
        }
        LOG.info("Solution takes " + steps.size() + " steps");

        return steps.toArray(new int[0][0]);
    }


    // Returns the row-major index of a cell in a 2D array
    private static int rowMajorIndex(int row, int col, int numCols) {
        return row * numCols + col;
    }

    // Creates a matrix that represents all potential toggles in a Lights Out game
    private static boolean[][] createToggleMatrix(int size) {
        boolean[][] toggleMatrix = new boolean[size * size][size * size];

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                toggleMatrix[rowMajorIndex(row, col, size)][rowMajorIndex(row, col, size)] = true;

                if (row > 0) {
                    toggleMatrix[rowMajorIndex(row - 1, col, size)][rowMajorIndex(row, col, size)] = true;
                }
                if (row < size - 1) {
                    toggleMatrix[rowMajorIndex(row + 1, col, size)][rowMajorIndex(row, col, size)] = true;
                }
                if (col > 0) {
                    toggleMatrix[rowMajorIndex(row, col - 1, size)][rowMajorIndex(row, col, size)] = true;
                }
                if (col < size - 1) {
                    toggleMatrix[rowMajorIndex(row, col + 1, size)][rowMajorIndex(row, col, size)] = true;
                }
            }
        }
        return toggleMatrix;
    }

    // Linearizes a 2D grid into a 1D array
    private static boolean[] linearizeLightsOutProblem(boolean[][] grid) {
        int size = grid.length;
        boolean[] linearized = new boolean[size * size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                linearized[row * size + col] = grid[row][col];
            }
        }
        return linearized;
    }

    // Finds the pivot row in a matrix when performing Gaussian elimination
    private static int findPivot(boolean[][] matrix, int currentRow, int currentCol) {
        for (int row = currentRow; row < matrix.length; row++) {
            if (matrix[row][currentCol]) {
                return row;
            }
        }
        return -1;
    }

    private static void swapRowsMatrix(boolean[][] matrix, int row1, int row2) {
        boolean[] temp = matrix[row1];
        matrix[row1] = matrix[row2];
        matrix[row2] = temp;
    }

    private static void swapRowsVector(boolean[] vector, int row1, int row2) {
        boolean temp = vector[row1];
        vector[row1] = vector[row2];
        vector[row2] = temp;
    }

    private static void performGaussianElimination(boolean[][] matrix, boolean[] puzzleVector) {
        int nextFreeRow = 0;

        for (int col = 0; col < matrix[0].length; col++) {
            int pivotRow = findPivot(matrix, nextFreeRow, col);

            if (pivotRow != -1) {
                swapRowsMatrix(matrix, nextFreeRow, pivotRow);
                swapRowsVector(puzzleVector, nextFreeRow, pivotRow);

                for (int row = nextFreeRow + 1; row < matrix.length; row++) {
                    if (matrix[row][col]) {
                        for (int i = 0; i < matrix[row].length; i++) {
                            matrix[row][i] = matrix[row][i] ^ matrix[nextFreeRow][i];
                        }
                        puzzleVector[row] = puzzleVector[row] ^ puzzleVector[nextFreeRow];
                    }
                }
                nextFreeRow++;
            }
        }
    }

    private static boolean[] performBackSubstitution(boolean[][] matrix, boolean[] puzzleVector) {
        boolean[] result = new boolean[matrix.length];


        for (int row = matrix.length - 1; row >= 0; row--) {
            int pivotCol = -1;
            for (int col = 0; col < matrix[row].length; col++) {
                if (matrix[row][col]) {
                    pivotCol = col;
                    break;
                }
            }

            if (pivotCol == -1) {
                if (puzzleVector[row]) {
                    Log.info("Problem has no solution");
                    return null;
                }
            } else {
                result[row] = puzzleVector[row];
                for (int i = pivotCol + 1; i < matrix[row].length; i++) {
                    // In the case of all 0's (the variable is redundant),
                    // the result is set to false to remove redundant clicks

                    // Because everything is in modulo 2, we can use XOR and AND operations
                    // to simplify the logic
                    result[row] = result[row] ^ (matrix[row][i] && result[i]);

                }
            }
        }
        return result;
    }

    private static boolean[][] solveLightsOut(boolean[][] grid) {
        int size = grid.length;
        boolean[] puzzleVector = linearizeLightsOutProblem(grid);
        boolean[][] toggleMatrix = createToggleMatrix(size);

        performGaussianElimination(toggleMatrix, puzzleVector);
        boolean[] result = performBackSubstitution(toggleMatrix, puzzleVector);

        // Puzzle has no solution
        if (result == null) {
            return null;
        }

        boolean [][] resultGrid = new boolean[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                resultGrid[row][col] = result[row * size + col];
            }
        }

        return resultGrid;
    }
}
