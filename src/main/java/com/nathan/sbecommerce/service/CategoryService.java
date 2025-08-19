package com.nathan.sbecommerce.service;

import com.nathan.sbecommerce.payload.request.CategoryDTO;
import com.nathan.sbecommerce.payload.response.CategoryRes;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {

    CategoryRes getCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    CategoryDTO createCategory(CategoryDTO category);

    CategoryDTO updateCategory(CategoryDTO categoryDto, Long categoryId);

    CategoryDTO deleteCategory(Long id);
}
