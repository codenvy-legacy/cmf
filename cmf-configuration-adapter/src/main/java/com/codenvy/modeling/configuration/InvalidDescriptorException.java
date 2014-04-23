package com.codenvy.modeling.configuration;

/**
 * Exception thrown in case if invalid descriptor occurred.
 *
 * @author Vladyslav Zhukovskii
 */
public class InvalidDescriptorException extends Exception {

    public InvalidDescriptorException(String message) {
        super(message);
    }

    public InvalidDescriptorException(String message, Throwable cause) {
        super(message, cause);
    }
}
