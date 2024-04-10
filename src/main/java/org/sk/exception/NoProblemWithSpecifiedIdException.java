package org.sk.exception;

public class NoProblemWithSpecifiedIdException extends RuntimeException {
    public final long id;
    public final String messageTemplate;
    public NoProblemWithSpecifiedIdException(long id) {
        super();
        this.id = id;
        this.messageTemplate = "No problem with id %d found.";
    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return String.format(messageTemplate, id);
    }
}
