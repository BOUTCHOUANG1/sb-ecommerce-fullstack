package com.nathan.sbecommerce.controller;

import com.nathan.sbecommerce.payload.request.CategoryDTO;
import com.nathan.sbecommerce.payload.response.CategoryRes;
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
    public ResponseEntity<CategoryRes> getAllCategories() {
        CategoryRes categories = categoryService.getCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("/admin/category")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDto) {
        CategoryDTO savedCategory = categoryService.createCategory(categoryDto);
         return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/category/{categoryid}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId) {
        CategoryDTO deletedCategoryDto = categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(deletedCategoryDto, HttpStatus.OK);
    }

    @PutMapping("/admin/category/{categoryid}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDto,
                                                 @PathVariable Long categoryId) {
        CategoryDTO savedCategory = categoryService.updateCategory(categoryDto, categoryId);
        return new ResponseEntity<>(savedCategory, HttpStatus.OK);
    }
}
