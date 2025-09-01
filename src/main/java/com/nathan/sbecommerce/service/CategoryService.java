package com.nathan.sbecommerce.service;

import com.nathan.sbecommerce.dto.request.CategoryRequest;
import com.nathan.sbecommerce.dto.response.CategoryResponse;
import org.springframework.stereotype.Service;


public interface CategoryService {

    CategoryResponse getCategories(Integer pageNumber,
                                   Integer pageSize,
                                   String sortBy, String sortOrder);

    CategoryRequest createCategory(CategoryRequest category);

    CategoryRequest updateCategory(CategoryRequest categoryRequest, Long categoryId);

    CategoryRequest deleteCategory(Long id);
}
