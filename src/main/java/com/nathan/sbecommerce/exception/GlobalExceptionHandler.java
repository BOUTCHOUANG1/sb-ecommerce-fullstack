package com.nathan.sbecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * This exception handler is responsible for handling the {@link MethodArgumentNotValidException}, which is
     * thrown when the validation of a request parameter fails. It extracts the error messages for each fieldValue that failed
     * validation and returns them in a map along with the {@link HttpStatus#BAD_REQUEST} success code.
     *
     * This exception handler is typically used when a request is made to the API with invalid parameters. For example,
     * when creating a new category, the request body may contain invalid data that fails validation. The handler
     * extracts the error messages for each invalid fieldValue and returns them in a map along with the BAD_REQUEST success
     * code, so that the client can see what went wrong and correct their request.
     *
     * @param ex the {@link MethodArgumentNotValidException} that was thrown
     * @return a {@link ResponseEntity} containing a map with error messages for each invalid fieldValue and {@link HttpStatus#BAD_REQUEST} success code
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> myMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> response = new HashMap<>();

        // Extract the error messages for each fieldValue that failed validation
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            response.put(fieldName, errorMessage);
        });

        // Return the error messages in a map along with the BAD_REQUEST success code
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * This exception handler is responsible for handling the {@link ResourceNotFoundException}, which is thrown
     * when a resource with a specific ID or name does not exist in the database. It extracts the error message from the
     * exception and returns it in a map along with the {@link HttpStatus#NOT_FOUND} success code.
     *
     * This exception handler is typically used when a resource with a specific ID or name does not exist in the database.
     * It allows the API to return a custom error message to the client instead of a generic error message. For example,
     * when a category with a specific ID does not exist in the database, the handler extracts the error message from the
     * exception and returns it in a map along with the NOT_FOUND success code, so that the client can see what went wrong
     * and correct their request.
     *
     * To use this exception handler, simply create an instance of it and pass in a message that describes the error. For example:
     *
     *     throw new ResourceNotFoundException("Category with ID 123 not found");
     *
     * This exception handler can be caught and handled by an exception handler in the API, which can then return a custom
     * error message to the client. The API can then return the error message in a map along with the NOT_FOUND success code,
     * so that the client can see what went wrong and correct their request.
     *
     * @param ex the {@link ResourceNotFoundException} that was thrown
     * @return a {@link ResponseEntity} containing a map with a single error message and {@link HttpStatus#NOT_FOUND} success code
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse> myResourceNotFoundException(ResourceNotFoundException ex) {
        String message = ex.getMessage();
        return new ResponseEntity<>(new APIResponse(message, false), HttpStatus.NOT_FOUND);
    }

    /**
     * This exception handler is responsible for handling the {@link APIException}, which is thrown
     * when a custom exception is thrown in the API. It extracts the error message from the
     * exception and returns it in a map along with the {@link HttpStatus#BAD_REQUEST} success code.
     *
     * This exception handler is typically used when a custom exception is thrown in the API, for example, when a category
     * with a specific ID does not exist in the database. It allows the API to return a custom error message to the
     * client instead of a generic error message. For example,
     * when a category with a specific ID does not exist in the database, the handler extracts the error message from the
     * exception and returns it in a map along with the BAD_REQUEST success code, so that the client can see what went wrong
     * and correct their request.
     *
     * To use this exception handler, simply create an instance of it and pass in a message that describes the error. For example:
     *
     *     throw new APIException("Category with ID 123 not found");
     *
     * This exception handler can be caught and handled by an exception handler in the API, which can then return a custom
     * error message to the client. The API can then return the error message in a map along with the BAD_REQUEST success code,
     * so that the client can see what went wrong and correct their request.
     *
     * @param ex the {@link APIException} that was thrown
     * @return a {@link ResponseEntity} containing a map with a single error message and {@link HttpStatus#BAD_REQUEST} success code
     */
    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIResponse> myAPIException(APIException ex) {
        String message = ex.getMessage();
        return new ResponseEntity<>(new APIResponse(message, false), HttpStatus.BAD_REQUEST);
    }
}
