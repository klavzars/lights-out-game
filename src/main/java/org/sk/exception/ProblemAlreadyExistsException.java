package org.sk.exception;

public class ProblemAlreadyExistsException extends RuntimeException {
    public final long id;
    public final String messageTemplate;
    public ProblemAlreadyExistsException(long id) {
        super();
        this.id = id;
        this.messageTemplate = "Problem already exists. Find the solution under Problem #%d.";
    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return String.format(messageTemplate, id);
    }
}
