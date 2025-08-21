package com.nathan.sbecommerce.exception;

import lombok.NoArgsConstructor;


/**
 * The APIException class represents an exception that can be thrown when an error occurs in the API.
 * It extends the RuntimeException class, which means that it is an unchecked exception, meaning that
 * it does not need to be explicitly declared in the method signature.
 *
 * This exception is typically used when a custom exception is thrown in the API, for example, when a category
 * with a specific ID does not exist in the database. It allows the API to return a custom error message to the
 * client instead of a generic error message.
 *
 * To use this exception, simply create an instance of it and pass in a message that describes the error. For example:
 *
 *     throw new APIException("Category with ID 123 not found");
 *
 * This exception can be caught and handled by an exception handler in the API, which can then return a custom
 * error message to the client.
 *
 * It is important to note that this exception is a runtime exception, which means that it does not need to be
 * declared in the method signature. However, it is still recommended to document the exceptions that can be thrown
 * in the method documentation.
 *
 * @author Nathan Haines
 * @since 1.0.0
 */
@NoArgsConstructor
public class APIException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new APIException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method)
     */
    public APIException(String message) {
        super(message);
    }
}
