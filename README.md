# Lights Out Game REST API

This is a simple REST API for the Lights Out Game, built using [Quarkus](https://quarkus.io/).
This document goes over some of the design decisions and implementation details of the application.

The frontend for this application can be found [here](https://github.com/klavzars/lights-out-frontend).


## Running the application
To run the application in dev mode, use:
```shell script
./mvnw compile quarkus:dev
```

## API usage
The API follows the assignment instructions and is documented using Swagger, which (in dev mode) can be accessed at
`http://localhost:8080/q/swagger-ui/`.

## Architecture
The application uses a layered architecture, with the following layers:
- **Resource**: The REST API layer, which handles incoming HTTP requests. The layer is split into:
    - **ProblemResource**: Handles requests related to getting and storing problems.
    - **SolutionResource**: Handles requests related to getting solutions.
- **Service**: The business logic layer. The layer is split into:
    - **ProblemService**: Handles the business logic related to problems.
    - **SolutionService**: Handles the business logic related to solutions.
    - **GameService**: Handles the business logic related to solving the Lights Out Game.
- **Repository**: The layer tasked with storing/retrieving data (the DB is simulated by an in-memory implementation). The layer is split into:
    - **ProblemRepository**: Handles the data storage and retrieval related to problems.
    - **SolutionRepository**: Handles the data storage and retrieval related to solutions.
- **Model**: The data model layer, which defines the data structures used in the application.
  - **Problem**: Represents a problem in the Lights Out Game.
  - **Solution**: Represents a solution to a problem in the Lights Out Game.
  - **SolutionStep**: Represents a step in a solution to a problem in the Lights Out Game.

### Database implementation
For the purposes of this assignment, I decided to simulate a database by creating it in-memory.
The table declarations (implemented as *ArrayLists*) can be found in the *Repository* files. The database 
consists of the tables specified in the assignment instructions:
- Problem
- Solution
- SolutionStep

**Note:** My initial idea didn't depend on the *SolutionStep* data, and I have already
developed a fair bit of the application before realizing that the instructions said I need to store the steps of the solution.
This is why the *SolutionStep* table is not used much in the application, but it is still implemented
for the sake of completeness.

### Error handling
The **exception** directory contains utilities for handling exceptions in the application.
The application handles various exceptions, such as:
- **NoProblemWithSpecifiedIdException**: Thrown when a problem with the specified ID does not exist.
- **ProblemAlreadyExistsException**: Thrown when a problem with the same grid the user is trying to create already exists.
- **ConstraintViolationException**: Used for validating the input Lights Out problem grid.

A **GenericThrowableMapper** is also implemented, which attempts to catch all other exceptions and 
returns a generic error message without leaking any sensitive information.

## Lights Out Algorithm Implementation

The algorithm used to solve the Lights Out Game is based on Gaussian Elimination.
The general idea (also described [here](https://mathworld.wolfram.com/LightsOutPuzzle.html))
is to represent the Lights Out Game as a system of linear equations and use the appropriate
algorithms (specifically, Gaussian Elimination and Back-Substitution) to solve the system.

The algorithm's execution time and the number of steps needed to solve the game are both 
being logged in the console.

The algorithm is implemented in the **GameService** class.

## Comments
Since I'm fairly new to Quarkus and Java EE, I realize that my implementation is far from perfect.
I still tried to follow best practices and keep the code clean and maintainable as much as
possible, but I'm sure there are many things that could be improved (things that come to mind are 
better error handling, implementing an actual database, and better input validation).

I'm looking forward to your feedback and want to thank you for the opportunity.


For any additional information, feel free to contact me at [klavzar.simon@gmail.com](mailto:klavzar.simon@gmail.com).
