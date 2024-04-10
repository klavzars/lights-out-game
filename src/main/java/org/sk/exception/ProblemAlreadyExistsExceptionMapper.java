package org.sk.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ProblemAlreadyExistsExceptionMapper implements ExceptionMapper<ProblemAlreadyExistsException> {
    @Override
    public Response toResponse(ProblemAlreadyExistsException e) {
        String errorMessage = String.format(e.getMessage(), e.getId());
        return Response.status(Response.Status.CONFLICT)
                .entity(new ErrorMessage(errorMessage))
                .build();
    }
}
