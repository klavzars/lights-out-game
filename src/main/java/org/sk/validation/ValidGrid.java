package org.sk.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GridValidator.class)
public @interface ValidGrid {
    String message() default "Invalid grid array. Should be a square boolean 2D array between 3x3 and 8x8.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
