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

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstant.SORT_DIR, required = false) String sortOrder
    ) {
        CategoryResponse categories = categoryService.getCategories(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("/admin/category")
    public ResponseEntity<CategoryRequest> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        CategoryRequest savedCategory = categoryService.createCategory(categoryRequest);
         return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/category/{categoryid}")
    public ResponseEntity<CategoryRequest> deleteCategory(@PathVariable Long categoryId) {
        CategoryRequest deletedCategoryRequest = categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(deletedCategoryRequest, HttpStatus.OK);
    }

    @PutMapping("/admin/category/{categoryid}")
    public ResponseEntity<CategoryRequest> updateCategory(@Valid @RequestBody CategoryRequest categoryRequest,
                                                          @PathVariable Long categoryId) {
        CategoryRequest savedCategory = categoryService.updateCategory(categoryRequest, categoryId);
        return new ResponseEntity<>(savedCategory, HttpStatus.OK);
    }
}
