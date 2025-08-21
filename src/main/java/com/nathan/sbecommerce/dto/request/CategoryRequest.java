package com.nathan.sbecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

/**
 * This class represents a data transfer object (DTO) for a category.
 * DTOs are used to transfer data between different layers of an application.
 * In this case, this class is used to transfer data about a category from the client to the server.
 *
 * The purpose of this class is to simplify the communication between the client (e.g., web browser) and the server.
 * It provides a convenient way to package and send data related to a category, such as its ID and name,
 * without exposing the internal details of the category object.
 *
 * This class is typically used when creating or updating a category. For example, when a user submits a form
 * to create a new category, the data from the form is packaged into a CategoryRequest object, which is then sent to the server.
 * The server can then use this object to create a new Category object and save it to the database.
 *
 * In summary, this class is a lightweight data container that makes it easier to transfer category data between the client and server.
 * It is used in the context of creating or updating a category.
 *
 * @author Nathan
 */
public class CategoryRequest {
    private Long categoryId;
    private String categoryName;
}
