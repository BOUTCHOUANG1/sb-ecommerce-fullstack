package com.nathan.sbecommerce.controller;

import com.nathan.sbecommerce.config.AppConstant;
import com.nathan.sbecommerce.dto.request.CategoryRequest;
import com.nathan.sbecommerce.dto.response.CategoryResponse;
import com.nathan.sbecommerce.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;


    /**
     * This method is used to retrieve a list of all categories from the database.
     * It is used to display a list of categories on the frontend of the e-commerce website.
     * It takes four parameters: pageNumber, pageSize, sortBy, and sortOrder.
     * pageNumber is used for pagination purposes and determines the page number of the categories list to be displayed.
     * pageSize determines the number of categories per page.
     * sortBy determines the field by which the categories will be sorted.
     * sortOrder determines the order in which the categories will be sorted.
     * The method returns a ResponseEntity object containing a CategoryResponse object.
     * CategoryResponse is a Data Transfer Object (DTO) that contains data related to a list of category requests.
     * It is used to transfer data between the backend and the frontend in the context of a category listing.
     * The content field is a list of CategoryRequest objects, which are used to display a list of categories.
     * The pageNumber, pageSize, totalElements, totalPages, and lastPage fields are used for pagination purposes.
     * This method is used in the following cases:
     * - When the user wants to view a list of categories.
     * - When the user wants to navigate through the categories by pages.
     * - When the user wants to filter or sort the categories list.
     *
     * @param pageNumber page number of the categories list to be displayed
     * @param pageSize number of categories per page
     * @param sortBy field by which the categories will be sorted
     * @param sortOrder order in which the categories will be sorted
     * @return ResponseEntity object containing a CategoryResponse object
     */
    @GetMapping("/public/category")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstant.SORT_DIR, required = false) String sortOrder
    ) {
        CategoryResponse categories = categoryService.getCategories(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }


    /**
     * This method is used to create a new category in the database. It takes a CategoryRequest object as a parameter,
     * which contains the details of the category to be created. It is an HTTP POST request mapped to the "/admin/category"
     * endpoint. The method uses the CategoryServiceImpl class to create the category in the database.
     *
     * The method returns a ResponseEntity object containing a CategoryRequest object. CategoryRequest is a Data Transfer Object
     * (DTO) that contains the details of a category. It is used to transfer data between the backend and the frontend in the context
     * of category creation.
     *
     * This method is used in the following cases:
     * - When an admin user wants to create a new category in the database.
     * - When an admin user wants to add a new category to the available categories list.
     * - When an admin user wants to add a new category to the database for future use.
     *
     * @param categoryRequest the CategoryRequest object containing the details of the category to be created.
     * @return ResponseEntity object containing a CategoryRequest object
     */
    @PostMapping("/admin/category")
    public ResponseEntity<CategoryRequest> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        CategoryRequest savedCategory = categoryService.createCategory(categoryRequest);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }


    /**
     * This method is used to delete an existing category from the database. It takes a categoryId as a parameter,
     * which is the identifier of the category to be deleted. It is an HTTP DELETE request mapped to the "/admin/category/{categoryid}"
     * endpoint. The method uses the CategoryServiceImpl class to delete the category from the database.
     *
     * The method returns a ResponseEntity object containing a CategoryRequest object. CategoryRequest is a Data Transfer Object
     * (DTO) that contains the details of a category. It is used to transfer data between the backend and the frontend in the context
     * of category deletion.
     *
     * This method is used in the following cases:
     * - When an admin user wants to delete an existing category from the database.
     * - When an admin user wants to remove a category from the available categories list.
     * - When an admin user wants to remove a category from the database.
     *
     * @param categoryId the identifier of the category to be deleted.
     * @return ResponseEntity object containing a CategoryRequest object
     */
    @DeleteMapping("/admin/category/{categoryid}")
    public ResponseEntity<CategoryRequest> deleteCategory(@PathVariable Long categoryId) {
        CategoryRequest deletedCategoryRequest = categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(deletedCategoryRequest, HttpStatus.OK);
    }


    /**
     * This method is used to update an existing category in the database. It takes a CategoryRequest object as a parameter,
     * which contains the updated details of the category. It also takes a categoryId as a parameter, which is the identifier of the
     * category to be updated. It is an HTTP PUT request mapped to the "/admin/category/{categoryid}"
     * endpoint. The method uses the CategoryServiceImpl class to update the category in the database with the new details.
     *
     * The method returns a ResponseEntity object containing a CategoryRequest object. CategoryRequest is a Data Transfer Object
     * (DTO) that contains the details of a category. It is used to transfer data between the backend and the frontend in the context
     * of category update.
     *
     * This method is used in the following cases:
     * - When an admin user wants to update the details of an existing category in the database.
     * - When an admin user wants to change the name of a category.
     * - When an admin user wants to update the description of a category.
     * - When an admin user wants to update the image of a category.
     *
     * @param categoryRequest the CategoryRequest object containing the updated details of the category.
     * @param categoryId the identifier of the category to be updated.
     * @return ResponseEntity object containing a CategoryRequest object
     */
    @PutMapping("/admin/category/{categoryid}")
    public ResponseEntity<CategoryRequest> updateCategory(@Valid @RequestBody CategoryRequest categoryRequest,
                                                          @PathVariable Long categoryId) {
        CategoryRequest savedCategory = categoryService.updateCategory(categoryRequest, categoryId);
        return new ResponseEntity<>(savedCategory, HttpStatus.OK);
    }
}
