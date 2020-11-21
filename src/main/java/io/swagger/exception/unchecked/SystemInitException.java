package io.swagger.exception.unchecked;

public class SystemInitException extends RuntimeException {

    /**
     * Constructs a new System initialisation Exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public SystemInitException(String message) {
        super(message);
    }
}
