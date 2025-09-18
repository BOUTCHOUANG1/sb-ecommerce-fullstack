package com.nathan.sbecommerce.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

/**
 * This class represents the response object for the API. It contains two fields:
 * `message` and `success`. The `message` fieldValue is a string that provides a description of
 * the result of the API operation. The `success` fieldValue is a boolean that indicates
 * whether the API operation was successful or not.
 *
 * This class is used to provide a standardized response for the API. It is used in
 * the exception handlers to provide a consistent response format for different types
 * of exceptions.
 *
 * For example, when a category with a specific ID does not exist in the database,
 * the API throws a `ResourceNotFoundException`. The exception handler captures
 * the exception, creates an `APIResponse` object with an appropriate error message
 * and sets the `success` fieldValue to `false`. This `APIResponse` object is then returned
 * as the response to the client.
 *
 * Another example is when the validation of the request body fails. The API throws
 * a `MethodArgumentNotValidException`. The exception handler captures the exception,
 * creates an `APIResponse` object with a map of fieldValue names to error messages and sets
 * the `success` fieldValue to `false`. This `APIResponse` object is then returned as the response
 * to the client.
 *
 * In both cases, the client can see the appropriate error message and the success of the
 * operation.
 */ 
public class APIResponse {
    /**
     * The description of the result of the API operation.
     */
    private String message;

    /**
     * Indicates whether the API operation was successful or not.
     */
    private boolean success;
}
