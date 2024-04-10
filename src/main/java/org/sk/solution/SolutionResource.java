package org.sk.solution;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.sk.exception.NoProblemWithSpecifiedIdException;
import org.sk.problem.Problem;

import java.util.ArrayList;

@Path("/solutions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Solutions", description = "Operations related to solutions")
public class SolutionResource {

    @Inject
    private SolutionService solutionService = new SolutionService();

    @GET
    @Operation(summary = "Get all solutions", description = "Returns a list of all solutions.")
    @APIResponse(responseCode = "200", description = "Returns all solutions",
            content = @Content(schema = @Schema(implementation = Solution.class, type = SchemaType.ARRAY)))
    public ArrayList<Solution> getAllSolutions() {
        return solutionService.getAllSolutions();
    }

    @GET
    @Path("/problem/{id}")
    @Operation(summary = "Get solution by problem id", description = "Returns a solution with specified problem id.")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Returns solution by problem id",
                    content = @Content(schema = @Schema(implementation = Solution.class))),
            @APIResponse(responseCode = "404", description = "Solution not found")
    })

    public Solution getSolutionByProblemId(@Parameter(name = "id", description = "Problem id", required = true) @PathParam("id") long id) {
        return solutionService.getSolutionByProblemId(id);
    }
}
