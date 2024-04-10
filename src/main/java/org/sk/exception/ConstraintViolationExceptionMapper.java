package org.sk.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

        @Override
        public Response toResponse(ConstraintViolationException e) {
            // To simplify things a bit I only return the first error message
            // (I only expect one error message from the validator anyway)
            String firstErrorMessage = e.getConstraintViolations().stream().findFirst().map(ConstraintViolation::getMessage).orElse("");

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorMessage(firstErrorMessage))
                    .build();
        }
}
