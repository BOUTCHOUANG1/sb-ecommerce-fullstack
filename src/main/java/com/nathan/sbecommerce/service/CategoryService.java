package com.nathan.sbecommerce.service;

import com.nathan.sbecommerce.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    List<Category> getCategories();

    void createCategory(Category category);

    Category updateCategory(Category category, Long categoryId);

    String deleteCategory(Long id);
}
