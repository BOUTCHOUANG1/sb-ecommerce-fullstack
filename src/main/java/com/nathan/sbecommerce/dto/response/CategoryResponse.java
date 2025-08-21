package com.nathan.sbecommerce.dto.response;

import com.nathan.sbecommerce.dto.request.CategoryRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * CategoryResponse is a Data Transfer Object (DTO) that contains data related to a list of category requests.
 * It is used to transfer data between the backend and the frontend in the context of a category listing.
 *
 * The content field is a list of CategoryRequest objects, which are used to display a list of categories.
 * The pageNumber, pageSize, totalElements, totalPages, and lastPage fields are used for pagination purposes.
 *
 * This class is used in the following cases:
 * - When the user wants to view a list of categories.
 * - When the user wants to navigate through the categories by pages.
 * - When the user wants to filter or sort the categories list.
 *
 * @author Nathan Pandelic
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    /** The list of category requests. */
    private List<CategoryRequest> content;
    /** The current page number. */
    private Integer pageNumber;
    /** The number of category requests per page. */
    private Integer pageSize;
    /** The total number of category requests. */
    private Long totalElements;
    /** The total number of pages for the category requests list. */
    private Integer totalPages;
    /** Indicates whether the current page is the last page of the category requests list. */
    private Boolean lastPage;
}
