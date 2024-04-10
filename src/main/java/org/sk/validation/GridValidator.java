package org.sk.validation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.jboss.logging.Logger;
import org.sk.problem.ProblemResource;

import java.util.Arrays;


public class GridValidator implements ConstraintValidator<ValidGrid, boolean[][]> {
    private static final Logger LOG = Logger.getLogger(ProblemResource.class);

    @Override
    public void initialize(ValidGrid constraintAnnotation) {
    }

    @Override
    public boolean isValid(boolean[][] grid, ConstraintValidatorContext context) {
        if (grid == null) {
            return false;
        }

        int length = grid.length;

        // Check if the array is between 3x3 and 8x8
        if (length < 3 || length > 8) {
            return false;
        }

        // Check if the array is nxn (square matrix)
        for (boolean[] subArray : grid) {
            if (subArray == null || subArray.length != length) {
                return false;
            }
        }
        return true;
    }
}