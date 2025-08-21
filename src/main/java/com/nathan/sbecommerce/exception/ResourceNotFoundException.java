package com.nathan.sbecommerce.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
/**
 * ResourceNotFoundException is an exception that is thrown when a requested resource is not found in the database.
 * This exception is typically used when the requested resource, such as a category or a product, is not found in the database.
 * When this exception is thrown, it provides a detailed error message that includes the name of the resource and the field
 * that was used to search for the resource. This exception is typically used in the API to return a custom error message to the
 * client instead of a generic error message.
 *
 * To use this exception, you need to provide the name of the resource, the field that was used to search for the resource, and
 * the value of the field that was used to search for the resource.
 *
 * For example, if a client requests a product with an ID of 100, but no product with ID 100 exists in the database, the API
 * can throw a ResourceNotFoundException with the resource name "Product", the field name "id", and the field value "100".
 * This will return a custom error message to the client instead of a generic error message.
 *
 * It is important to note that this exception is a checked exception, which means that it needs to be handled or declared
 * in the method that throws it. This is typically done by adding a throws clause to the method signature.
 *
 * @author Nathan Hirst
 * @version 1.0
 * @since 1.0
 */
public class ResourceNotFoundException extends RuntimeException {
    String resourceName;
    String fieldName;
    Long fieldId;
    String field;

    /**
     * Constructs a new ResourceNotFoundException with the specified resource name, field name, and field value.
     *
     * @param resourceName the name of the resource that was not found
     * @param fieldName the name of the field that was used to search for the resource
     * @param field the value of the field that was used to search for the resource
     */
    public ResourceNotFoundException(String resourceName, String fieldName, String field) {
        super(String.format("%s not found with %s : '%s'", resourceName, field, fieldName));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldName = fieldName;
    }

    /**
     * Constructs a new ResourceNotFoundException with the specified resource name, field name, and field ID.
     *
     * This constructor is used when a resource is not found with a specific ID. It is typically used when a client
     * requests a resource with a specific ID, but no resource with that ID exists in the database. This exception is
     * used to return a custom error message to the client instead of a generic error message.
     *
     * For example, if a client requests a category with an ID of 100, but no category with ID 100 exists in the database,
     * the API can throw a ResourceNotFoundException with the resource name "Category", the field name "id", and the
     * field ID "100". This will return a custom error message to the client instead of a generic error message.
     *
     * @param resourceName the name of the resource that was not found
     * @param field the name of the field that was used to search for the resource
     * @param fieldId the value of the field that was used to search for the resource
     */
    public ResourceNotFoundException(String resourceName, String field, Long fieldId) {
        super(String.format("%s not found with %s : '%d'", resourceName, field, fieldId));
        this.resourceName = resourceName;
        this.fieldName = field;
        this.fieldId = fieldId;
    }
}
