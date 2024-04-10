package org.sk.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NoProblemWithSpecifiedIdExceptionMapper implements ExceptionMapper<NoProblemWithSpecifiedIdException> {

    @Override
    public Response toResponse(NoProblemWithSpecifiedIdException e) {
        String errorMessage = String.format(e.getMessage(), e.getId());
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorMessage(errorMessage))
                .build();
    }
}
