package org.sk.problem;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;
import org.sk.solution.Solution;
import org.sk.validation.ValidGrid;

import java.util.ArrayList;
import java.util.Collections;


@Path("/problems")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Problems", description = "Operations related to problems")
public class ProblemResource {

    private static final Logger LOG = Logger.getLogger(ProblemResource.class);

    @Inject
    private ProblemService problemService = new ProblemService();

    @GET
    @Operation(summary = "Get all problems", description = "Returns a list of all problems.")
    @APIResponse(responseCode = "200", description = "List of all problems",
            content = @Content(schema = @Schema(implementation = Problem.class, type = SchemaType.ARRAY)))
    public ArrayList<Problem> getProblems() {
        ArrayList<Problem> problems = problemService.getAllProblems();

        return problemService.getAllProblems();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get problem by id", description = "Returns a problem with specified id.")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Returns problem by id",
                    content = @Content(schema = @Schema(implementation = Problem.class))),
            @APIResponse(responseCode = "404", description = "Problem not found")
    })
    public Problem getProblemById(@Parameter(name = "id", description = "Problem id", required = true)
                                      @PathParam("id") String id) {
        return problemService.getProblemById(Integer.parseInt(id));
    }

    @POST
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Saves problem and returns solution",
                    content = @Content(schema = @Schema(implementation = Solution.class))),
            @APIResponse(responseCode = "400", description = "Invalid problem input")
    })
    @Operation(summary = "Add problem and find solution",
            description = "Adds a problem and returns a solution. " +
                    "If solution does not exist, returns a Solution object " +
                    "with id's set to -1 and solution set to null.")
    public Solution addProblemAndFindSolution(
            @RequestBody(
                    description = "Problem to be added. " +
                            "Expects a 2D boolean array of size from (including) 3x3 to (including) 8x8",
                    required = true,
                    content = @Content(schema = @Schema(implementation = boolean[][].class)))
            @Valid @ValidGrid boolean[][] problem) {
        return problemService.storeAndSolveProblem(problem);

    }

}

